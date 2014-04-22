package crazycar.persistent.spaces;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

import crazycar.logic.data.Direction;
import crazycar.persistent.Id;

@SpaceClass
public class DirectionSpace  implements Serializable{

	private static final long serialVersionUID = 2051499763849972019L;
	private Id id;
	private String direction;
	
	public DirectionSpace(){}

	public DirectionSpace(Id id, String direction) {
		this.id = id;
		this.direction = direction;
	}
	
    public static DirectionSpace valueOf(Direction c){
    	return new DirectionSpace(new Id(c),c.name());
    }
    
    public Direction toDirection(){
    	return Direction.valueOf(direction);
    }

    @SpaceId(autoGenerate = false)
	public Id getId() {
		return id;
	}
	
	public void setId(Id id) {
		this.id = id;
	}
	
    @SpaceProperty
	public String getDirection() {
		return direction;
	}
	
	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((direction == null) ? 0 : direction.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		DirectionSpace other = (DirectionSpace) obj;
		if (direction == null) {
			if (other.direction != null)
				return false;
		} else if (!direction.equals(other.direction))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "DirectionSpace[direction=" + direction + "]";
	}
}
