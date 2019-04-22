package github.lightningcreations.game.level.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;

import github.lightningcreations.game.ResourceName;

public class SpriteDataCollisionBox implements Data {
	private int len, height;
	private int directions;
	private float frictionCoefficient;
	private Reader r;
	static class Reader implements Data.Reader{
		Reader(String id,Map<ResourceName,Data.Reader> target){
			target.put(new ResourceName(id), this);
		}
		@Override
		public Data read(DataInputStream strm) throws IOException {
			int len = strm.readUnsignedShort();
			int height = strm.readUnsignedShort();
			int directions = strm.readUnsignedByte();
			float friction = strm.readFloat();
			return new SpriteDataCollisionBox(this,len,height,directions,friction);
		}

		@Override
		public void write(Data fdata, DataOutputStream strm) throws IOException {
			SpriteDataCollisionBox box = (SpriteDataCollisionBox)fdata;
			strm.writeShort(box.len);
			strm.writeShort(box.height);
			strm.writeByte(box.directions);
			strm.writeFloat(box.frictionCoefficient);
		}

		@Override
		public Data defaultData() {
			// TODO Auto-generated method stub
			return new SpriteDataCollisionBox(this);
		}
		
	}
	
	public SpriteDataCollisionBox(Reader r) {
		this.r = r;
	}
	public SpriteDataCollisionBox(Reader r,int len,int width,int directions,float friction) {
		this.r = r;
		this.len = len;
		this.height = width;
		this.directions = directions;
		this.frictionCoefficient = friction;
	}
	private static final String[] fields = {"Length","Height","Directions","Friction"};
	
	@Override
	public String[] getFieldNames() {
		// TODO Auto-generated method stub
		return fields.clone();
	}
	@Override
	public Class<?> getClassForField(String name) {
		switch(name) {
		case "Length":
		case "Height":
		case "Directions":
			return int.class;
		case "Friction":
			return float.class;
		default:
			throw new IllegalArgumentException("No such field");
		}
	}
	@Override
	public Object getField(String name) {
		switch(name) {
		case "Length":
			return len;
		case "Height":
			return height;
		case "Directions":
			return directions;
		case "Friction":
			return frictionCoefficient;
		default:
			throw new IllegalArgumentException("No such field");
		}
	}
	@Override
	public void setField(String name, Object value) {
		switch(name) {
		case "Length":
			len = (int)value;
		break;
		case "Height":
			height = (int)value;
		break;
		case "Directions":
			directions = ((int)value)&0x0f;
		break;
		case "Friction":
			frictionCoefficient = (float)value;
		break;
		default:
			throw new IllegalArgumentException("No such field");
		}
		
	}
	@Override
	public Reader getReader() {
		// TODO Auto-generated method stub
		return r;
	}

}
