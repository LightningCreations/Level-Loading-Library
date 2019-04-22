package github.lightningcreations.game.level.format;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import github.lightningcreations.game.ResourceName;
import github.lightningcreations.game.level.data.Data;

public class Sprite {
	private ResourceName id;
	private int posX;
	private int posY;
	private int spriteFlags;
	private Data data;
	
	public Sprite() {
		// TODO Auto-generated constructor stub
	}
	
	public Sprite(ResourceName id,int posX,int posY,int spriteFlags) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.spriteFlags = spriteFlags&0x5f;
		this.data = Data.getSpriteReader(id).defaultData();

	}
	public Sprite(ResourceName id,int posX,int posY,int spriteFlags,Data data) {
		this.id = id;
		this.posX = posX;
		this.posY = posY;
		this.spriteFlags = spriteFlags&0x5f;
		this.data = data;
	}
	
	public Data getData() {
		return data;
	}
	
	public ResourceName getId() {
		return id;
	}
	public void setPosX(int posX) {
		this.posX = posX;
	}
	public int getPosX() {
		return posX;
	}
	public void setPosY(int posY) {
		this.posX = posY;
	}
	public void setFlags(int flags) {
		this.spriteFlags |= flags&0x5f;
	}
	public void clearFlags(int flags) {
		this.spriteFlags &= ~(flags&0x5f);
	}
	public int getFlags() {
		return spriteFlags;
	}
	
	public void write(DataOutputStream dout) throws IOException{
		dout.writeUTF(id.toString());
		dout.writeShort(posX);
		dout.writeShort(posY);
		dout.writeByte(spriteFlags);
		data.write(dout);
	}
	
	public void read(DataInputStream din) throws IOException{
		String tmpId = din.readUTF();
		if(!tmpId.matches(Constants.RESOURCE_NAME_REGEX))
			throw new IOException("File Rejected: id is the id of the sprite. It MUST match the regex [A-Za-z_-][\\w_-]*:[A-Za-z_-][\\w_-]*(\\/[A-Za-z_-][\\w_-]*)*.");
		id = new ResourceName(tmpId);
		posX = din.readUnsignedShort();
		posY = din.readUnsignedShort();
		spriteFlags = din.readUnsignedByte();
		if((spriteFlags&0xA0)!=0)
			throw new IOException("File Rejected: spriteFlags 0x80 and 0x20 MUST NOT be set");
		data = Data.getSpriteReader(id).read(din);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + posX;
		result = prime * result + posY;
		result = prime * result + spriteFlags;
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
		Sprite other = (Sprite) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (posX != other.posX)
			return false;
		if (posY != other.posY)
			return false;
		if (spriteFlags != other.spriteFlags)
			return false;
		return true;
	}

}
