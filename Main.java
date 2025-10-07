import functions.*;
import functions.basic.Sin;

public class Main {
    public static void main(String[] args) throws Exception {
        TabulatedFunction[] functions = new TabulatedFunction[4];

        functions[0] = tabulate(new Sin(), 0, 10, 11, Types.ARRAY);
        functions[1] = (TabulatedFunction) functions[0].clone();
        functions[2] = tabulate(new Sin(), 0, 10, 11, Types.LINKED_LIST);
        functions[3] = (TabulatedFunction) functions[2].clone();

        functions[1].setPointX(0, -1);
        functions[3].setPointX(0, -1);

        System.out.println("Сравниваем нулевой с остальными: ");
        for (int i = 0; i < functions.length; i++) {
            System.out.println(functions[i].equals(functions[3]));
        }
        System.out.println("Коды: ");
        for (int i = 0; i < functions.length; i++) {
            System.out.println(functions[i].hashCode());
        }
        System.out.println("toString():");
        for (int i = 0; i < functions.length; i++) {
            System.out.println(functions[i].toString());
        }
    }
    enum Types {
        ARRAY,
        LINKED_LIST
    }
    public static TabulatedFunction tabulate(Function function, double leftX, double rightX, int pointsCount, Types currentType) throws IllegalArgumentException {
        if (leftX >= rightX) throw new IllegalArgumentException("leftX must be less than rightX");
        if (leftX < function.getLeftDomainBorder()) throw new IllegalArgumentException("leftX too low");
        if (rightX > function.getRightDomainBorder()) throw new IllegalArgumentException("rightX too high");
        if (pointsCount < 2) throw new IllegalArgumentException("pointsCount must be greater than 1");
        FunctionPoint[] points = new FunctionPoint[pointsCount];
        double step = (rightX - leftX) / (pointsCount - 1);
        double x;
        for (int i = 0; i < pointsCount; i++) {
            x = leftX + step * i;
            points[i] = new FunctionPoint(x, function.getFunctionValue(x));
        }
        return switch (currentType) {
            case ARRAY -> new ArrayTabulatedFunction(points);
            case LINKED_LIST -> new LinkedListTabulatedFunction(points);
        };
    }
}