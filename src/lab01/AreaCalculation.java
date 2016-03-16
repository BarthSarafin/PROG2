package lab01;

/**
 * @author Stefan R. Bachmann on  03.02.2016
 * @version v0.1 - lab01
 */
public class AreaCalculation {

	private double a;
	private double b;
	private double c;
	private double halfsum;
	private double area;

	public AreaCalculation(double a, double b, double c){
		this.a = a;
		this.b = b;
		this.c = c;
	}

    public double calculateTriangle(double a, double b, double c) throws NotATriangleException {

        if(isTriangle(a,b,c) && isNotZero(a) && isNotZero(b) && isNotZero(c)) {
            this.halfsum = (a + b + c) / 2;
            this.area = Math.sqrt(halfsum * (halfsum - a) * (halfsum - b) * (halfsum - c));
            return area;
        } else {
            throw new NotATriangleException("Your values do not form a triangle or are not valid.");
        }
    }

    protected boolean isTriangle(double a, double b, double c){

        double temp;
        if(a > c && a > b){
            temp = a;
            a = c;
            c = temp;
        } else if(b > c){
            temp = b;
            b = c;
            c = temp;
        }
        //return (Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2)); // rechtiwinkliges Dreieck!
		return a + b > c && a + c > b && b + c > a;
	}

    private boolean isNotZero(double number){
        return (number > 0);
    }
	public double getA() {
		return a;
	}
	public double getB() {
		return b;
	}
	public double getC() {
		return c;
	}
}

