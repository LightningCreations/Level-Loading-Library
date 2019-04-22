package github.lightningcreations.game;

import github.lightningcreations.lclib.Hash;

public final class ResourceName implements Comparable<ResourceName> {
	private final String domain;
	private final String path;

	
	public ResourceName(String name) {
		this(name.split(":"));
	}
	private ResourceName(String[] parts) {
		this(parts[0],parts[1]);
	}
	public ResourceName(String domain,String path) {
		this.domain = domain;
		this.path = path;
	}
	
	public String toString() {
		return domain+":"+path;
	}
	@Override
	public int compareTo(ResourceName arg0) {
		int compare;
		if((compare=domain.compareToIgnoreCase(arg0.domain))!=0)
			return compare;
		else
			return path.compareToIgnoreCase(arg0.path);
	}
	@Override
	public int hashCode() {
		return Hash.hashcode(toString());
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResourceName other = (ResourceName) obj;
		if (domain == null) {
			if (other.domain != null)
				return false;
		} else if (!domain.equals(other.domain))
			return false;
		if (path == null) {
			if (other.path != null)
				return false;
		} else if (!path.equals(other.path))
			return false;
		return true;
	}
	

}
