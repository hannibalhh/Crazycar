package crazycar.logic.data;

public class Roxel {

	private final Direction direction;
	private final Location location;
	private final Car car;

	public Roxel(Direction direction, Location location, Car car) {
		this.direction = direction;
		this.location = location;
		this.car = car;
	}

	public static Roxel valueOf(Direction direction, Location location, Car car) {
		return new Roxel(direction, location,car);
	}
	
	public static Roxel empty(Direction direction, Location location) {
		return new Roxel(direction, location,Car.empty);
	}
	
	public static Roxel car(Direction direction, Location location) {
		return new Roxel(direction, location,Car.ferrari);
	}

	public Direction getDirection() {
		return direction;
	}

	public Location getLocation() {
		return location;
	}
	
	public Car getCar() {
		return car;
	}

	public Roxel nextRoxel(){
		return Roxel.valueOf(direction, nextLocation(),car);
	}
	
	public Roxel toCar(){
		return Roxel.valueOf(direction,location,Car.ferrari);	
	}

	public Location nextLocation() {
		if (direction.equals(Direction.north))
			return location.add(Location.valueOf(0, -1));
		else if (direction.equals(Direction.east))
			return location.add(Location.valueOf(1, 0));
		else if (direction.equals(Direction.south))
			return location.add(Location.valueOf(0, 1));
		else if (direction.equals(Direction.west))
			return location.add(Location.valueOf(-1, 0));
		else
			return location;
	}

	@Override
  public int hashCode() {
	  final int prime = 31;
	  int result = 1;
	  result = prime * result + ((car == null) ? 0 : car.hashCode());
	  result = prime * result + ((direction == null) ? 0 : direction.hashCode());
	  result = prime * result + ((location == null) ? 0 : location.hashCode());
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
	  Roxel other = (Roxel) obj;
	  if (car == null) {
		  if (other.car != null)
			  return false;
	  } else if (!car.equals(other.car))
		  return false;
	  if (direction != other.direction)
		  return false;
	  if (location == null) {
		  if (other.location != null)
			  return false;
	  } else if (!location.equals(other.location))
		  return false;
	  return true;
  }

	@Override
  public String toString() {
	  return "Roxel [direction=" + direction + ", location=" + location + ", car=" + car + "]";
  }


}
