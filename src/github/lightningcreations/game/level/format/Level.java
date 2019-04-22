package github.lightningcreations.game.level.format;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import github.lightningcreations.game.ResourceName;
import github.lightningcreations.lclib.Hash;

public class Level {
	private Header head;
	private List<Layer> layers = new ArrayList<>();
	private List<Sprite> sprites = new ArrayList<>();
	private List<Trigger> triggers = new ArrayList<>();
	public Level(String name,int len,int width) {
		head = new Header(name,len,width);
	}
	public Level() {
		head = new Header();
	}
	
	public String getLevelId() {
		return head.getLevelId();
	}
	
	public int getLength() {
		return head.getLength();
	}
	public int getWidth() {
		return head.getWidth();
	}
	public void setLength(int len) {
		head.setLength(len);
	}
	public void setWidth(int width) {
		head.setWidth(width);
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 0;
		result = prime * result + ((head == null) ? 0 : head.hashCode());
		result = prime * result + ((layers == null) ? 0 : Hash.hashcode(layers));
		result = prime * result + ((sprites == null) ? 0 : Hash.hashcode(sprites));
		result = prime * result + ((triggers == null) ? 0 : Hash.hashcode(triggers));
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
		Level other = (Level) obj;
		if (head == null) {
			if (other.head != null)
				return false;
		} else if (!head.equals(other.head))
			return false;
		if (layers == null) {
			if (other.layers != null)
				return false;
		} else if (!layers.equals(other.layers))
			return false;
		if (sprites == null) {
			if (other.sprites != null)
				return false;
		} else if (!sprites.equals(other.sprites))
			return false;
		if (triggers == null) {
			if (other.triggers != null)
				return false;
		} else if (!triggers.equals(other.triggers))
			return false;
		return true;
	}
	public void save(DataOutputStream out) throws IOException{
		head.write(out);
		out.writeShort(layers.size());
		for(Layer l:layers)
			l.write(out);
		out.writeShort(sprites.size());
		for(Sprite s:sprites)
			s.write(out);
		out.writeShort(triggers.size());
		for(Trigger t:triggers)
			t.write(out);
		out.writeInt(hashCode());
	}
	public void load(DataInputStream in) throws IOException{
		layers.clear();
		sprites.clear();
		triggers.clear();
		head.read(in);
		
		int len = in.readUnsignedShort();
		for(int i = 0;i<len;i++) {
			Layer l = new Layer();
			l.read(in);
			layers.add(l);
		}
		len = in.readUnsignedShort();
		for(int i = 0;i<len;i++) {
			Sprite s = new Sprite();
			s.read(in);
			sprites.add(s);
		}
		len = in.readUnsignedShort();
		for(int i = 0;i<len;i++) {
			Trigger s = new Trigger();
			s.read(in);
			triggers.add(s);
		}
		if(in.readInt()!=hashCode())
			throw new IOException("File Rejected: hash MUST match the hashcode of the structure");
	}
	
	public Layer addLayer() {
		if(layers.size()>=65536)
			throw new IllegalStateException("Cannot add more than 65535 layers");
		Layer l = new Layer();
		layers.add(l);
		return l;
	}
	public Sprite addSprite(ResourceName id) {
		if(sprites.size()>=65536)
			throw new IllegalStateException("Cannot add more than 65535 sprites");
		Sprite s = new Sprite(id,0,0,0);
		sprites.add(s);
		return s;
	}
	public Trigger addTrigger(ResourceName id) {
		if(triggers.size()>=65536)
			throw new IllegalStateException("Cannot add more than 65535 triggers");
		Trigger t = new Trigger(id);
		triggers.add(t);
		return t;
	}
}
