package github.lightningcreations.game.level.format;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import github.lightningcreations.lclib.Patterns;
import github.lightningcreations.lclib.Version;

public class Header {
	private Version ver;
	private String id;
	private int len;
	private int width;
	public Header(String id, int length,int width) {
		this.id = id;
		assert length>0&&length<65536;
		assert width>0&&width<65536;
		this.len = length;
		this.width = width;
		this.ver = Constants.CURRENT;
	}
	public Header(){
		
	}
	
	public void write(DataOutputStream dout) throws IOException {
		dout.writeInt(Constants.MAGIC);
		ver.write(dout);
		dout.writeUTF(id);
		dout.writeUTF("");
		dout.writeByte(0);
		dout.writeShort(len);
		dout.writeShort(width);
	}
	
	public void read(DataInputStream din) throws IOException{
		if(din.readInt()!=Constants.MAGIC)
			throw new IOException("File Rejected: magic MUST be exactly the 4 bytes [EE AB DC 0B]");
		ver = Version.read(din);
		if(ver.compareTo(Constants.CURRENT)>0)
			throw new IOException("Unsupported Version: "+ver);
		id = din.readUTF();
		if(!id.matches(Patterns.identifier))
			throw new IOException("File Rejected: levelid is the identifier of the level. It MUST match the regular expression [A-Za-z_][\\w_]*");
		if(!din.readUTF().isEmpty())
			throw new IOException("File Rejected: unused_levelName is unused and reserved. It MUST be an empty string");
		if(din.readUnsignedByte()!=0)
			throw new IOException("File Rejected: unused_flags is unused and reserved. It MUST be set to [00]");
		len = din.readUnsignedShort();
		width = din.readUnsignedShort();
	}
	
	public String getLevelId() {
		return id;
	}
	public int getLength() {
		return len;
	}
	public int getWidth() {
		return width;
	}
	public void setLength(int length) {
		assert length>0&&length<65536;
		this.len = length;
	}
	public void setWidth(int width) {
		assert width>0&&width<65536;
		this.width = width;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + len;
		result = prime * result + ((ver == null) ? 0 : ver.hashCode());
		result = prime * result + width;
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
		Header other = (Header) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (len != other.len)
			return false;
		if (ver == null) {
			if (other.ver != null)
				return false;
		} else if (!ver.equals(other.ver))
			return false;
		if (width != other.width)
			return false;
		return true;
	}
	

}
