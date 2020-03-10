package frc.jwood.VisionRPi;
import org.opencv.core.Mat;

/**
 * images for merge process
 */
public class Image
{
    public static final String pId = new String("[Image]");
    
    public Mat mat = new Mat();
    public boolean isFreshImage = false;

    public synchronized void setImage(Mat mat)
    {
        mat.copyTo(this.mat);
        this.isFreshImage = true;
        notify();
    }

    public synchronized void getImage(Mat mat)
    {
        if (!this.isFreshImage)
        {
            try
            {
                wait();
            } 
            catch (Exception e)
            {
                System.out.println(pId + " error " + e);
            }
        }
        this.isFreshImage = false;
        this.mat.copyTo(mat);
    }

    public synchronized boolean isFreshImage()
    {
        return this.isFreshImage;
    }
}