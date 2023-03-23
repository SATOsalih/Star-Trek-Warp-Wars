package startrekwarpwars;

public class Stack {

	private int top;
	private Object[] elements;
	
	Stack(int capacity){
		
		elements=new Object[capacity];
		top=-1;
	}
	
	void push(Object data) {
		
		if(isFull()) {
			
			
		}
		else {
			
			top++;
			elements[top]=data;
		}
	}
	
	Object pop() {
		
		if(isEmpty()) {
			
			return null;
		}
		else {
			Object retData=elements[top];
			top--;
			return retData;
		}
	}
	Object peek() {
		if(isEmpty()) {
			
			return null;
		}
		else {
			
			return elements[top];
		}
	}
	boolean isEmpty() {
		return(top==-1);
	}
	boolean isFull() {
		return (top +1 ==elements.length);
	}
	int size() {
		return top+1;
	}
	
	
}
