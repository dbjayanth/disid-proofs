package org.springframework.roo.petclinic.domain;

import org.apache.commons.codec.binary.Base64;
import org.springframework.util.Assert;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;

import javax.imageio.ImageIO;
import javax.persistence.Embeddable;

/**
 * = Image
 * 
 * DTO that contains all the necessary operations to manage an image.
 * 
 * Also, it provides some utility methods to obtain info about the contained image and to apply
 * format and resize of the image..
 *  
 * @author jcgarcia at http://www.disid.com[DISID Corporation S.L.]
 */
@Embeddable
public class Image {

  private static final String DATA_IMAGE_PREFIX = "data:image/";

  private static final String BASE64_TYPE = ";base64,";

  /**
   * Byte array that contains all the image
   * information.
   */
  private byte[] image;

  /**
   * Default constructor
   */
  public Image() {
    // Nothing to do here
  }

  /**
   * Constructor that receives the byte array information
   * to initialize the image.
   * 
   * @param image
   */
  public Image(byte[] image) {
    this.image = image;
  }

  /**
   * Getter that returns the image byte array.
   * 
   * @return the bytes of the image
   */
  public byte[] getImage() {
    return this.image;
  }

  /**
   * Setter that sets the bytes of the image.
   * 
   * @param image
   */
  public void setImage(byte[] image) {
    this.image = image;
  }

  /**
   * Utility method that obtains the format of the uploaded image using the
   * byte array.
   * 
   * @return String with the format of the uploaded image.
   */
  public String getFormat() {
    Assert.notNull(this.image, "ERROR: The provided image should have a valid image byte array.");
    try {
      return URLConnection.guessContentTypeFromStream(new ByteArrayInputStream(this.image))
          .replace("image/", "");
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }


  /**
   * Utility method that obtains the {@link Image} in base64 format.
   * 
   * @return String with base64 format.
   */
  public String getBase64() {
    Assert.notNull(this.image, "ERROR: The provided image should have a valid image byte array.");
    return Base64.encodeBase64String(this.image);
  }

  /**
   * Utility method that resizes the image using the received width and height
   * 
   * @param width The new width of the image
   * @param height The new height of the image
   * @return Image the same image but resized to the specified dimensions.
   */
  public Image resize(int width, int height) {

    try {
      // First of all, obtain the buffered image from the provided one
      BufferedImage imageBuffer = getBufferedImage();

      // Obtain the image type
      int type = imageBuffer.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : imageBuffer.getType();

      // Creating new BufferedImage with the new resized size
      BufferedImage resizedImage = new BufferedImage(width, height, type);

      // Creating graphics that will create the resize image
      Graphics2D g = resizedImage.createGraphics();
      g.drawImage(imageBuffer, 0, 0, width, height, null);
      g.dispose();

      // Return the resized image
      return writeImage(resizedImage, getFormat());

    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }


  /**
   * Utility method that formats the image using the received format.
   * 
   * @param newFormat String that indicates the new format of the image.
   * @return Image the same image but in the new format.
   */
  public Image format(String newFormat) {
    try {
      // Write the new image in the new provided format
      return writeImage(getBufferedImage(), newFormat);
    } catch (IOException e) {
      throw new RuntimeException(e.getMessage());
    }
  }

  /**
   * Utility method that formats and resize the image at the same time
   * delegating in <code>format</code> and <code>resize</code>.
   * 
   * @param newFormat The new format to use
   * @param width The new width of the image
   * @param height The new height of the image
   * @return Image resized and formatted.
   */
  public Image formatAndResize(String newFormat, int width, int height) {
    Image formattedImage = format(newFormat);
    return formattedImage.resize(width, height);
  }

  /**
   * Method that obtains a buffered image using the current byte arrays.
   * 
   * @return BufferedImage with all the information about the image
   * @throws IOException if some error during the image read.
   */
  private BufferedImage getBufferedImage() throws IOException {
    // Obtain the input stream of the provided image
    InputStream imageInputStream = new ByteArrayInputStream(this.image);
    // Create a bufferedImage using the ImageIO utilities
    BufferedImage imageBuffer = ImageIO.read(imageInputStream);

    if (imageBuffer == null) {
      throw new RuntimeException("ERROR: The provided ." + getFormat() + " image is not valid.");
    }

    return imageBuffer;
  }

  /**
   * Method that writes the provided bufferedImage with the provided format and returns
   * a new instance of the image.
   *  
   * @param image The bufferedImage to use
   * @param format The format of the new image
   * @return Image instance with the new byteArray.
   * @throws IOException
   */
  private Image writeImage(BufferedImage image, String format) throws IOException {
    ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();
    ImageIO.write(image, format, byteArrayOut);
    return new Image(byteArrayOut.toByteArray());
  }

  /**
   * toString method that returns the String representation of this image. It delegates
   * into the <code>getFormat()</code> and the <code>getBase64()</code> methods to obtain 
   * the complete representation of the image.
   * 
   * @return String that could be used into an "src" attribute of an img HTML element.
   */
  @Override
  public String toString() {
    return DATA_IMAGE_PREFIX.concat(getFormat()).concat(BASE64_TYPE).concat(getBase64());
  }


}
