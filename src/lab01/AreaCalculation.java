package lab01;

/**
 * @author Stefan R. Bachmann on  11.02.2016
 * @version v0.1 - lab01
 */
public class AreaCalculation {

    public double calculateTriangle(double a, double b, double c) throws NotATriangleException {

        if(isTriangle(a,b,c) && isNotZero(a) && isNotZero(b) && isNotZero(c)) {
            double halfsum = (a + b + c) / 2;
            double area = Math.sqrt(halfsum * (halfsum - a) * (halfsum - b) * (halfsum - c));
            return area;
        } else {
            throw new NotATriangleException("Your values do not form a triangle or are not valid.");
        }
    }

    private boolean isTriangle(double a, double b, double c){

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
        return (Math.pow(a, 2) + Math.pow(b, 2) == Math.pow(c, 2));
    }

    private boolean isNotZero(double number){
        return (number > 0);
    }
}
