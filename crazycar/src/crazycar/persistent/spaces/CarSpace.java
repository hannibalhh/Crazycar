package crazycar.persistent.spaces;

import java.io.Serializable;

import com.gigaspaces.annotation.pojo.SpaceClass;
import com.gigaspaces.annotation.pojo.SpaceId;
import com.gigaspaces.annotation.pojo.SpaceProperty;

import crazycar.logic.data.Car;
import crazycar.persistent.Id;

@SpaceClass
public class CarSpace implements Serializable{
	
	private static final long serialVersionUID = -7600971277589862101L;
	private Id id;
	private Boolean empty;
	
	public CarSpace(){}
	
    public CarSpace(Id id, Boolean empty) {
		this.id = id;
		this.empty = empty;
	}
    
    public static CarSpace valueOf(Car c){
    	return new CarSpace(new Id(c),c.isEmpty());
    }
    
    public Car toCar(){
    	return Car.valueOf(empty);
    }

    @SpaceId(autoGenerate = false)
    public Id getId() {
        return id;
    }
    public void setId(Id id) {
        this.id = id;
    }
	
    @SpaceProperty
	public Boolean isEmpty(){
		return empty;
	}
		
	public void setEmpty(Boolean empty) {
		this.empty = empty;
	}

	@Override
	public String toString() {
		return "CarSpace[empty=" + empty + "]";
	}
}
