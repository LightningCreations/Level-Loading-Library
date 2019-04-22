package github.lightningcreations.game.level.format;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import github.lightningcreations.game.ResourceName;
import github.lightningcreations.game.level.data.Data;

public class Trigger {
	private ResourceName id;
	private Data data;
	
	public Trigger() {
		// TODO Auto-generated constructor stub
	}
	
	public Trigger(ResourceName id) {
		this.id = id;
		this.data = Data.getTriggerReader(id).defaultData();
	}
	public Trigger(ResourceName id,Data data) {
		this.id = id;
		this.data = data;
	}
	
	public ResourceName getId() {
		return id;
	}
	public Data getData() {
		return data;
	}
	public void read(DataInputStream strm) throws IOException {
		String tmp_id = strm.readUTF();
		if(!tmp_id.matches(Constants.RESOURCE_NAME_REGEX))
			throw new IOException("id MUST match the regex [A-Za-z_-][\\w_-]*:[A-Za-z_-][\\w_-]*(\\/[A-Za-z_-][\\w_-]*)*");
		id = new ResourceName(tmp_id);
		data = Data.getTriggerReader(id).read(strm);
	}
	public void write(DataOutputStream strm) throws IOException{
		strm.writeUTF(id.toString());
		data.write(strm);
	}

	@Override
	public int hashCode() {
		return id.hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Trigger other = (Trigger) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	

}
