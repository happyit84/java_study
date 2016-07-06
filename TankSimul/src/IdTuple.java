
public class IdTuple {
	int id1;
	int id2;
	
	public IdTuple(int id1, int id2){
		setIdTuple(id1, id2);
	}
	
	void setIdTuple(int id1, int id2){
		if( id1 < id2 ){
			this.id1 = id1;
			this.id2 = id2;
		}else{
			this.id1 = id2;
			this.id2 = id1;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id1;
		result = prime * result + id2;
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
		IdTuple other = (IdTuple) obj;
		if (id1 != other.id1)
			return false;
		if (id2 != other.id2)
			return false;
		return true;
	}
	
	
}
