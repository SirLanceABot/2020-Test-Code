package frc.jwood.VisionRPi;
/*

If this program fails in away that prevents uploading a new version then temporarily rename the /boot/frc.json to something else
and restart the program.  If this cannot be done by login to the RPi then put the micro SD boot flash drive in a PC and rename
the file.

The program will exit immediately failing to read the frc.json.  Then the RPi should be functional enough to upload a new version.

Note there are some settable parameters located below the SKULL in the right wide scroller.

The change from the standard FRCVISION example project is:

To get rid of "errors" in the VS Code source presentation, change the .classpath to see the libraries as needed.
As of the early 2020 release this wasn't necessary.

A tasks.json task is added to VS Code to run helpful commands made for the team:
(Access these commands on the VSC command palette ctrl-shift-P / Tasks: Run Task)
    Creation of the project build cmd file:
        RPiVisionCompile.cmd
    Creation of PowerShell Script to display the UDP message output (the optional thread can also do this)
        receiveUDP.ps1
====

Make a backup of the frc.json by copying it somewhere (example below is to the current working directory)
Windows command line:
scp -o UserKnownHostsFile=/dev/null -o StrictHostKeyChecking=no pi@frcvision.local:/boot/frc.json frc.json

This program below starts execution in Main.java - main.java

Threads are spawned (optionally) for
    CameraProcessB (TurretB camera)
 
Image processing threads are then spawned for
    PipelineProcessB
    ImageOperator (show cartoon of the location of the high target)
--

Some of this project is based on the frc provided example thus:
*/
/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.invoke.MethodHandles;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.opencv.core.Core;

import edu.wpi.cscore.MjpegServer;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.cscore.VideoSource;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.networktables.EntryListenerFlags;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;

/*
   JSON format:  Note that the < and > are not entered in the file
   {
       "team": <team number>,
       "ntmode": <"client" or "server", "client" if unspecified>,
       "cameras": [
           {
               "name": <camera name>,
               "path": <path, e.g. "/dev/video0">,
               "pixel format": <"MJPEG", "YUYV", etc>,   // optional
               "width": <video mode width>,              // optional
               "height": <video mode height>,            // optional
               "fps": <video mode fps>,                  // optional
               "brightness": <percentage brightness>,    // optional
               "white balance": <"auto", "hold", value>, // optional
               "exposure": <"auto", "hold", value>,      // optional
               "properties": [                           // optional
                   {
                       "name": <property name>,
                       "value": <property value>
                   }
               ],
               "stream": {                               // optional
                   "properties": [
                       {
                           "name": <stream property name>,
                           "value": <stream property value>
                       }
                   ]
               }
           }
       ]
       "switched cameras": [
           {
               "name": <virtual camera name>
               "key": <network table key used for selection>
               // if NT value is a string, it's treated as a name
               // if NT value is a double, it's treated as an integer index
           }
       ]
   }
 */

public final class VisionInit
{
    static {System.out.println("Starting class: " + MethodHandles.lookup().lookupClass().getCanonicalName());}

    static
    { // sleep needed for early version of this code in 2020.  Linux or JVM allowed this program to start before
      // Linux or JVM was fully functional to run correctly the threads spawned by this program
        try
        {
            Thread.sleep(10000);
        }
        catch (InterruptedException ex) 
        { }
    }

     static final String pId = new String("[Main]");

     //static String configFile = "/boot/frc.json";
     static String configFile = "/home/lvuser/deploy/frc.json";

    @SuppressWarnings("MemberName")
    public static class CameraConfig
    {
        public String name;
        public String path;
        public int height;
        public int width;
        public JsonObject config;
        public JsonElement streamConfig;
    }

    @SuppressWarnings("MemberName")
    public static class SwitchedCameraConfig
    {
        public String name;
        public String key;
    }

    public static class CameraWidget
    {
        public String name;

        public int column;
        public int row;
        public int width;
        public int height;

        // Name	Type        Default     Value	Notes
        // Show crosshair	Boolean     true	Show or hide a crosshair on the image
        // Crosshair color	Color	    "white"	Can be a string or a rgba integer
        // Show controls	Boolean	    true	Show or hide the stream controls
        // Rotation	        String	    "NONE"	Rotates the displayed image. One of ["NONE", "QUARTER_CW", "QUARTER_CCW", "HALF"]
 
        public boolean showCrosshair;
        public String crosshairColor;
        public boolean showControls;
        public String rotation;

        public void setLocation(int row, int column, int height, int width)
        {
            this.column = column;
            this.width = width;
            this.height = height;
            this.width = width;
        }

        public void setProperties(boolean showCrosshair, String crosshairColor, boolean showControls, String rotation)
        {
            this.showCrosshair = showCrosshair;
            this.crosshairColor = crosshairColor;
            this.showControls = showControls;
            this.rotation = rotation;
        }
    }

     static CameraProcessB cpB;
    // static CameraProcessE cpE;
     static ImageOperator imageOperator;

     static Thread visionThreadB;
    // static Thread visionThreadE;
     static Thread imageOperatorThread;

    static Object tabLock;
    static ShuffleboardTab cameraTab;

    static double tapeDistance = -1.;
    static double tapeAngle = -1.;
    static int tapeContours = -1;
    static boolean isTargetFound = false;
    static Object tapeLock;
    static boolean isDistanceAngleFresh = false;
    static double calibrateAngle;

// Settable parameters for some outputs listed below the skull


    // _______________uu$$$$$$$$$$uu______________
    // ____________uu$$$$$$$$$$$$$$$$uu___________
    // __________u$$$$$$$$$$$$$$$$$$$$$u__________
    // _________u$$$$$$$$$$$$$$$$$$$$$$$u_________
    // ________u$$$$$$$$$$$$$$$$$$$$$$$$$u________
    // ________u$$$$$$$$$$$$$$$$$$$$$$$$$u________
    // ________u$$$$$$"___"$$$"___"$$$$$$u________
    // ________"$$$$"______u$u_______$$$$"________
    // _________$$$________u$u_______u$$$_________
    // _________$$$u______u$$$u______u$$$_________
    // __________"$$$$uu$$$___$$$uu$$$$"__________
    // ___________"$$$$$$$"___"$$$$$$$"___________
    // _____________u$$$$$$$u$$$$$$$u_____________
    // ______________u$"$"$"$"$"$"$u______________
    // ___uuu________$$u$_$_$_$_$u$$_______uuu____
    // __u$$$$________$$$$$u$u$u$$$_______u$$$$___
    // ___$$$$$uu______"$$$$$$$$$"_____uu$$$$$$___
    // _u$$$$$$$$$$$uu____"""""____uuuu$$$$$$$$$$_
    // _$$$$"""$$$$$$$$$$uuu___uu$$$$$$$$$"""$$$"_
    // __"""______""$$$$$$$$$$$uu_""$"""__________
    // ____________uuuu_""$$$$$$$$$$uuu___________
    // ___u$$$uuu$$$$$$$$$uu_""$$$$$$$$$$$uuu$$$__
    // ___$$$$$$$$$$""""___________""$$$$$$$$$$$"_
    // ____"$$$$$"______________________""$$$$""__



// Settable parameters for some outputs listed below
// Settable parameters for some outputs listed below
// Settable parameters for some outputs listed below

    static String version = "roboRIO Vision 3/9/2020"; // change this everytime

    // camera streams already available from the FRCVISION server
    // generated video streams switched on/off here
    static boolean runImageOperator = true;
    static boolean displayTurretContours = true;
 
    static boolean displayTurretPixelDistance = false; // calibration info for pixels to inches distance to target
    //static boolean displayTurretHistogram = true; // a small insert for turrent contours - experiment with white balance - suggest using about 3500K then forget it.  Run GRIP after setting.

    static boolean debug = false;
    
// Shuffleboard automatic display of intake camera and High Power Port alignment turned on.
//
// No Shuffleboard automatic display of other video streams - commented out for contour images in TargetSelection codes
// and Turret Camera below here in main.  You can always drag and drop the streams within Shuffleboard if you want to see them.
/////////////////////////////////////////////////////////////////////////////////////////////////////

    public static int team;
    public static boolean server;
    public static List<CameraConfig> cameraConfigs = new ArrayList<>();
    public static List<SwitchedCameraConfig> switchedCameraConfigs = new ArrayList<>();
    public static List<VideoSource> cameras = new ArrayList<>();
 
     VisionInit()
    {
    }

    /**
     * Report parse error.
     */
     static void parseError(String str)
    {
        System.err.println(pId + " config error in '" + configFile + "': " + str);
    }

     static String output(InputStream inputStream) throws IOException
    {
        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;

        try
        {
            br = new BufferedReader(new InputStreamReader(inputStream));
            String line = null;
            while ((line = br.readLine()) != null)
            {
                sb.append(line + System.getProperty("line.separator"));
            }
        }
        finally
        {
            br.close();
        }

        return sb.toString();
    }

    /**
     * Read single camera configuration.
     */
    public static boolean readCameraConfig(JsonObject config)
    {
        CameraConfig cam = new CameraConfig();

        // name
        JsonElement nameElement = config.get("name");
        if (nameElement == null) 
        {
            parseError("could not read camera name");
            return false;
        }
        cam.name = nameElement.getAsString();

        // path
        JsonElement pathElement = config.get("path");
        if (pathElement == null) 
        {
            parseError("camera '" + cam.name + "': could not read path");
            return false;
        }
        cam.path = pathElement.getAsString();

        // height
        JsonElement heightElement = config.get("height");
        if (heightElement == null)
        {
            parseError("camera " + cam.name + ": could not read height");
        }
        cam.height = heightElement.getAsInt();

        // width
        JsonElement widthElement = config.get("width");
        if (widthElement == null)
        {
            parseError("camera " + cam.name + ": could not read width");
        }
        cam.width = widthElement.getAsInt();

        // stream properties
        cam.streamConfig = config.get("stream");

        cam.config = config;

        cameraConfigs.add(cam);

        return true;
    }

    /**
     * Read single switched camera configuration.
     */
    public static boolean readSwitchedCameraConfig(JsonObject config)
    {
        SwitchedCameraConfig cam = new SwitchedCameraConfig();

        // name
        JsonElement nameElement = config.get("name");
        if (nameElement == null)
        {
            parseError("could not read switched camera name");
            return false;
        }
        cam.name = nameElement.getAsString();

        // path
        JsonElement keyElement = config.get("key");
        if (keyElement == null)
        {
            parseError("switched camera '" + cam.name + "': could not read key");
            return false;
        }
        cam.key = keyElement.getAsString();

        switchedCameraConfigs.add(cam);

        return true;
    }

    /**
     * Read configuration file.
     */
    @SuppressWarnings("PMD.CyclomaticComplexity")
    public static boolean readConfig()
    {
        // parse file
        JsonElement top;
        try
        {
            top = new JsonParser().parse(Files.newBufferedReader(Paths.get(configFile)));
        }
        catch (IOException ex)
        {
            System.err.println(pId + " could not open '" + configFile + "': " + ex + "\n");
            return false;
        }

        // top level must be an object
        if (!top.isJsonObject())
        {
            parseError("must be JSON object");
            return false;
        }

        JsonObject obj = top.getAsJsonObject();

        // team number
        JsonElement teamElement = obj.get("team");
        if (teamElement == null)
        {
            parseError("could not read team number");
            return false;
        }
        team = teamElement.getAsInt();

        // ntmode (optional)
        if (obj.has("ntmode")) 
        {
            String str = obj.get("ntmode").getAsString();
            if ("client".equalsIgnoreCase(str)) 
            {
                server = false;
            } 
            else if ("server".equalsIgnoreCase(str)) 
            {
                server = true;
            } 
            else
            {
                parseError("could not understand ntmode value '" + str + "'");
            }
        }

        // cameras
        JsonElement camerasElement = obj.get("cameras");
        if (camerasElement == null) 
        {
            parseError("could not read cameras");
            return false;
        }

        JsonArray cameras = camerasElement.getAsJsonArray();
        for (JsonElement camera : cameras) 
        {
            if (!readCameraConfig(camera.getAsJsonObject())) 
            {
                return false;
            }
        }

        if (obj.has("switched cameras"))
        {
            JsonArray switchedCameras = obj.get("switched cameras").getAsJsonArray();
            for (JsonElement camera : switchedCameras) 
            {
                if (!readSwitchedCameraConfig(camera.getAsJsonObject()))
                {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * Start running the camera.
     */
    public static VideoSource startCamera(CameraConfig config) 
    {
        System.out.println(pId + " Starting camera '" + config.name + "' on path " + config.path);

        // this
        CameraServer inst = CameraServer.getInstance();
        UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();//= new UsbCamera(config.name, config.path);
        
        MjpegServer server = inst.startAutomaticCapture(camera);

        // or this and need port to be passed in
        // UsbCamera camera = new UsbCamera(config.name, config.path);
        // MjpegServer server = new MjpegServer("serve_" + config.name, port);
        // server.setSource(camera);

        Gson gson = new GsonBuilder().create();

        camera.setConfigJson(gson.toJson(config.config));
        camera.setConnectionStrategy(VideoSource.ConnectionStrategy.kKeepOpen);

        if (config.streamConfig != null) 
        {
            server.setConfigJson(gson.toJson(config.streamConfig));
        }

        return camera;
    }

    /**
     * Start running the switched camera.
     */
    public static MjpegServer startSwitchedCamera(SwitchedCameraConfig config)
    {
        System.out.println("Starting switched camera '" + config.name + "' on " + config.key);
        MjpegServer server = CameraServer.getInstance().addSwitchedCamera(config.name);

        NetworkTableInstance.getDefault()
            .getEntry(config.key)
            .addListener(event -> 
            {
                if (event.value.isDouble())
                {
                    int i = (int) event.value.getDouble();
                    if (i >= 0 && i < cameras.size()) 
                    {
                        server.setSource(cameras.get(i));
                    }
                }
                else if (event.value.isString())
                {
                    String str = event.value.getString();
                    for (int i = 0; i < cameraConfigs.size(); i++) 
                    {
                        if (str.equals(cameraConfigs.get(i).name)) 
                        {
                            server.setSource(cameras.get(i));
                            break;
                        }
                    }
                }
            },
            EntryListenerFlags.kImmediate | EntryListenerFlags.kNew | EntryListenerFlags.kUpdate);

        return server;
    }

     static void createCameraShuffleboardWidget(VideoSource camera, CameraWidget cw)
    {
        // Name	Type            Default     Value	    Notes
        // -----------------    ---------   --------    ----------------------------------------------------
        // Show crosshair	    Boolean     true	    Show or hide a crosshair on the image
        // Crosshair color	    Color	    "white"	    Can be a string or a rgba integer
        // Show controls	    Boolean	    true	    Show or hide the stream controls
        // Rotation	            String	    "NONE"	    Rotates the displayed image. 
        //                                              One of ["NONE", "QUARTER_CW", "QUARTER_CCW", "HALF"]
       
        Map<String, Object> cameraWidgetProperties = new HashMap<String, Object>();
        cameraWidgetProperties.put("Show crosshair", cw.showCrosshair);
        cameraWidgetProperties.put("Crosshair color", cw.crosshairColor);
        cameraWidgetProperties.put("Show controls", cw.showControls);
        cameraWidgetProperties.put("Rotation", cw.rotation);
           
        synchronized(VisionInit.tabLock)
        {
            VisionInit.cameraTab.add(cw.name + " Camera", camera)
                .withWidget(BuiltInWidgets.kCameraStream)
                .withPosition(cw.column, cw.row)
                .withSize(cw.width, cw.height)
                .withProperties(cameraWidgetProperties);
            Shuffleboard.update();
        }
    }

    /**
     * Main.
     */
    public static void setup(String... args)
    {
        Thread.currentThread().setName("4237Main");
        System.out.println(pId + " ***** setup method starting *****");

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        if (args.length > 0) 
        {
            configFile = args[0];
        }

        // read configuration
        if (!readConfig()) 
        {
            System.out.println(pId + " FATAL ERROR - could not read camera configuration file " + configFile);
            return;
        }

        tabLock = new Object();
        tapeLock = new Object();
            
        // start NetworkTables
        NetworkTableInstance ntinst = NetworkTableInstance.getDefault();
        if (server) 
        {
            System.out.println(pId + " Setting up NetworkTables server");
            ntinst.startServer();
        } 
        else 
        {
            System.out.println(pId + " Setting up NetworkTables client for team " + team);
            ntinst.startClientTeam(team);
        }

        // Create the Camera tab on the shuffleboard
        synchronized(tabLock)
        {
            cameraTab = Shuffleboard.getTab("Camera");
        }
 
        System.out.flush();

        // start cameras
        for (CameraConfig config : cameraConfigs) 
        {
            // assume each camera name appears only once in the list - that is a requirement
            System.out.println(pId + " Checking for " + config.name + " camera");
            if (config.name.equalsIgnoreCase("Turret"))
            {
                System.out.println(pId + " Starting TurretB camera");
                VideoSource Bcamera = startCamera(config);

                // Widget in Shuffleboard Tab
                CameraWidget cw = new CameraWidget();
                cw.name = config.name;
                cw.setLocation(0, 20, 13, 13);
                cw.setProperties(false, "white", false, "NONE");
                // comment or not Shuffleboard Widget to display Turret Camera
                // Normally this camera shows only the reflected tape rotated 90 deg - confusing to humans
                // createCameraShuffleboardWidget(Bcamera, cw);                

                cpB = new CameraProcessB(Bcamera, config);
                visionThreadB = new Thread(cpB, "4237TurretB Camera");
                // start thread using the class' run() method (just saying run() won't start a thread - that just runs run() once)
                visionThreadB.start(); 
            }
            else
            {
                System.out.println(pId + " Unknown camera in cameraConfigs " + config.name);
            }
        }
        
        // start switched cameras
        for (SwitchedCameraConfig config : switchedCameraConfigs) 
        {
            startSwitchedCamera(config);
        }

        if(runImageOperator)
        {
            // start thread to process image for High Power Port Alignment "cartoon"
            try
            {
                // Wait for other processes to make some images otherwise first time though gets
                // an error
                Thread.sleep(2000);
            }
            catch (InterruptedException ex)
            { }

            imageOperator = new ImageOperator();
            imageOperatorThread = new Thread(imageOperator, "4237ImageOperator");
            imageOperatorThread.start();
        }
    }
}
