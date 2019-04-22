package github.lightningcreations.game.level.data;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import github.lightningcreations.game.ResourceName;

public interface Data {
	public String[] getFieldNames();
	public Class<?> getClassForField(String name);
	public Object getField(String name);
	public void setField(String name,Object value);
	public Reader getReader();
	public static interface Reader{
		public Data read(DataInputStream strm) throws IOException;
		public void write(Data data,DataOutputStream strm) throws IOException;
		public Data defaultData();
	}
	public static final Empty.Reader EMPTY_READER_INSTANCE = new Empty.Reader();
	public static final class Empty implements Data{
		protected byte[] raw;
		private static class Reader implements Data.Reader{

			@Override
			public Empty read(DataInputStream strm) throws IOException {
				int len = strm.readUnsignedShort();
				byte[] data = new byte[len];
				strm.readFully(data);
				return new Empty(this,data);
			}

			@Override
			public void write(Data fdata, DataOutputStream strm) throws IOException {
				Empty data = (Empty)fdata;
				strm.writeShort(data.raw.length);
				strm.write(data.raw);
			}

			@Override
			public Empty defaultData() {
				return new Empty(this,new byte[0]);
			}
			
		}
		private final Reader reader;
		private static final String[] fields = {"bytes"};
		private Empty(Reader r,byte[] raw) {
			this.reader = r;
			this.raw = raw;
		}

		public Reader getReader() {
			return reader;
		}

		@Override
		public String[] getFieldNames() {
			// TODO Auto-generated method stub
			return fields.clone();
		}
		

		@Override
		public Object getField(String name) {
			if(name.equals("bytes"))
				return raw;
			throw new IllegalArgumentException("No such field");
		}

		@Override
		public void setField(String name, Object value) {
			if(name.equals("bytes"))
				raw = (byte[])value;
			else
			throw new IllegalArgumentException("No such field");
		}

		@Override
		public Class<?> getClassForField(String name) {
			if(name.equals("bytes"))
				return byte[].class;
			throw new IllegalArgumentException("No such field");
		}
	}
	public static Reader getSpriteReader(ResourceName id) {
		return DataHelpers.getSpriteReader(id,EMPTY_READER_INSTANCE);
	}
	public static Reader getTriggerReader(ResourceName id) {
		return DataHelpers.getTriggerReader(id,EMPTY_READER_INSTANCE);
	}
	
	public default void write(DataOutputStream strm) throws IOException {
		getReader().write(this, strm);
	}
}
