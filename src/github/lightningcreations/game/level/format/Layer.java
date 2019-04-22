package github.lightningcreations.game.level.format;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Layer {
	private String image;
	private float scrollSpeed;
	public Layer(String image) {
		this.image = image;
		assert image.matches(Constants.IMAGE_PATH_REGEX);
	}
	public Layer(String image,float scrollSpeed) {
		this.image = image;
		this.scrollSpeed = scrollSpeed;
		assert image.matches(Constants.IMAGE_PATH_REGEX);
	}
	public Layer() {
		
	}
	public String getImage() {
		assert image!=null;
		return image;
	}
	public float getScrollSpeed() {
		return scrollSpeed;
	}
	public void setImage(String image) {
		assert image.matches(Constants.IMAGE_PATH_REGEX);
		this.image = image;
	}
	public void setScrollSpeed(float scrollSpeed) {
		this.scrollSpeed = scrollSpeed;
	}
	public void write(DataOutputStream stream) throws IOException {
		stream.writeByte(0);
		stream.writeUTF(image);
		stream.writeFloat(scrollSpeed);
	}
	public void read(DataInputStream stream) throws IOException{
		if(stream.readUnsignedByte()!=0)
			throw new IOException("File Rejected: unused_layerFlags is unused and reserved. It MUST be the byte [00]");
		image = stream.readUTF();
		if(!image.matches(Constants.IMAGE_PATH_REGEX))
			throw new IOException("File Rejected: layerImagePath is the path to which the level image can be found. It MUST match the regex [^/ ]*(/[^/ ]*)*/?");
		scrollSpeed = stream.readFloat();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((image == null) ? 0 : image.hashCode());
		result = prime * result + Float.floatToIntBits(scrollSpeed);
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Layer other = (Layer) obj;
		if (image == null) {
			if (other.image != null)
				return false;
		} else if (!image.equals(other.image))
			return false;
		if (Float.floatToIntBits(scrollSpeed) != Float.floatToIntBits(other.scrollSpeed))
			return false;
		return true;
	}
}
