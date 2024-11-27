import org.jetbrains.annotations.NotNull;

import java.util.*;

import java.util.regex.Pattern;


public class mainClass {
    public LinkedList<Algebra> arrayOfAlgebras;

    public mainClass() {
        this.arrayOfAlgebras = new LinkedList<>();
    }

    public void inputAlgebra(String inputAlgebra) {
        this.arrayOfAlgebras.add(parser(inputAlgebra));
    }

    public void newAlgebra(String name, HashSet<Integer> elements, HashSet<String> operations, HashSet<Integer> neutralElements) {
        this.arrayOfAlgebras.add(new Algebra(name, elements, operations, neutralElements));
    }

    public void cloneElement(@NotNull Algebra e) {
        newAlgebra("Clone_" + e.name, e.elements, e.operations, e.neutralElements);
    }

    // Метод для добавления элемента в множество
    public void addElement(@NotNull Algebra e, int elementToAdd) {
        e.addElement(elementToAdd);
    }

    // Метод для удаления элемента из множества
    public void removeElement(@NotNull Algebra e, int elementToRemove) {
        e.removeElement(elementToRemove);
    }

    // Метод для добавления операции в множество операций
    public void addOperation(@NotNull Algebra e, String operationToAdd) {
        e.addOperation(operationToAdd);
    }

    // Метод для удаления операции из множества операций
    public void removeOperation(@NotNull Algebra e, String operationToRemove) {
        e.removeOperation(operationToRemove);
    }


    // Метод для добавления нейтрального элемента
    public void addNeutralElement(@NotNull Algebra e, int neutralElementToAdd) {
        e.addNeutralElement(neutralElementToAdd);
    }

    // Метод для удаления нейтрального элемента
    public void removeNeutralElement(@NotNull Algebra e, int neutralElementToRemove) {
        e.removeNeutralElement(neutralElementToRemove);
    }

    // Геттер для получения множества элементов
    public Set<Integer> getElements(@NotNull Algebra e) {
        return e.elements;
    }

    // Геттер для получения множества операций
    public Set<String> getOperations(@NotNull Algebra e) {
        return e.operations;
    }

    // Геттер для получения множества нейтральных элементов
    public Set<Integer> getNeutralElements(@NotNull Algebra e) {
        return e.neutralElements;
    }

    public Algebra parser(String input) {
        Pattern pattern1 = Pattern.compile("[{<]");
        String[] matcher1 = pattern1.split(input);
        String nameOfAlgebra = matcher1[0];

        String setAndOperationsOfAlgebra = matcher1[1].substring(0, matcher1[1].length() - 1);
        Pattern pattern2 = Pattern.compile(";");
        String[] matcher2 = pattern2.split(setAndOperationsOfAlgebra);
        String set = matcher2[0];
        String operations = matcher2[1];
        String neutralElements;
        Pattern pattern5;
        String[] matcher5;
        HashSet<Integer> neutralElementsForAlgebra;
        try {
            neutralElements = matcher2[2];

            pattern5 = Pattern.compile(",");
            matcher5 = pattern5.split(neutralElements);
            neutralElementsForAlgebra = new HashSet<>();
            for (String s : matcher5) {
                try {
                    neutralElementsForAlgebra.add(Integer.parseInt(s));
                } catch (Exception e) {
                    System.out.println("Error of operations");
                }
            }

        } catch (Exception e) {
            neutralElementsForAlgebra = new HashSet<>();
        }

        Pattern pattern3 = Pattern.compile(",");
        String[] matcher3 = pattern3.split(set);

        HashSet<Integer> setForAlgebra = new HashSet<>();
        for (String string : matcher3) {
            try {
                setForAlgebra.add(Integer.parseInt(string));
            } catch (Exception e) {
                System.out.println("Error of set");
            }
        }

        Pattern pattern4 = Pattern.compile(",");
        String[] matcher4 = pattern4.split(operations);
        HashSet<String> operationsForAlgebra = new HashSet<>();
        for (String s : matcher4) {
            try {
                operationsForAlgebra.add(s);
            } catch (Exception e) {
                System.out.println("Error of operations");
            }
        }
        return new Algebra(nameOfAlgebra, setForAlgebra, operationsForAlgebra, neutralElementsForAlgebra);
    }
    public String buildExpression(int operand1, String operation, int operand2) {
        return operand1 + " " + operation + " " + operand2;
    }

    public int reduceExpression(@NotNull String expression) {
        String[] parts = expression.split(" ");
        int operand1 = Integer.parseInt(parts[0]);
        int operand2 = Integer.parseInt(parts[2]);
        String operation = parts[1];

        switch (operation) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                if (operand2 != 0) {
                    return operand1 / operand2;
                } else {
                    System.out.println("Division by zero is not allowed.");
                    return 0;
                }
            case "^":
                return (int) Math.pow(operand1, operand2);
            default:
                System.out.println("Unsupported operation: " + operation);
                return 0;
        }
    }

    public static @NotNull List reduceElement(@NotNull Algebra e) {
        List<Integer> result = new LinkedList<>();
        int operand1 = 0;
        int operand2;
        int temp = 0;

        for (Integer operand : e.elements) {
            operand2 = operand1;
            operand1 = operand;

            if (temp != 0) {
                for (String operation : e.operations) {
                    switch (operation) {
                        case "+" -> result.add(operand1 + operand2);
                        case "-" -> result.add(operand1 - operand2);
                        case "*" -> result.add(operand1 * operand2);
                        case "^" -> result.add((int) Math.pow(operand1, operand2));
                        case "/" -> {
                            if (operand2 != 0) {
                                result.add(operand1 / operand2);
                            } else {
                                System.out.println("Division by zero is not allowed.");
                            }
                        }
                        default -> System.out.println("Unsupported operation: " + operation);
                    }
                }
            } else {
                temp++;
            }
        }
        return result;
    }

    // 0 - без наворотов
    // 1 - отображение по экспоненте
    // 2 - отображение по натуральному логорифму
    // 3 - отображение элемента в квадрате
    // остальное - по мере необходимости
    public static Algebra createMorph(@NotNull Algebra inputAlgebra, int inputRule) {
        HashSet<Integer> setForAlgebra = new HashSet<>();
        for (int setElement : inputAlgebra.elements) {
            switch (inputRule) {
                case (0) -> setForAlgebra.add(setElement);
                case (1) -> setForAlgebra.add((int) Math.exp(setElement));
                case (2) -> setForAlgebra.add((int) Math.log(setElement));
                case (3) -> setForAlgebra.add((int) Math.pow(setElement, 2));
            }
        }
        return new Algebra("Morphism_" + inputAlgebra.name, setForAlgebra, inputAlgebra.operations, inputAlgebra.neutralElements);
    }

    public static boolean checkMapping(@NotNull Algebra a, @NotNull Algebra b) {
        boolean result;
        Algebra aCloneMorf;
        boolean injectiveMapping = false;
        for (int i = 0; i <= 3; i++) {
            result = true;
            aCloneMorf = createMorph(a, i);
            if (a.elements.size() > b.elements.size()) {
                result = false;
            } else {
                try {
                    if ((a.neutralElements.size() > b.neutralElements.size())) result = false;
                } catch (Exception e) {
                    if (!(a.neutralElements == null & b.neutralElements == null)) result = false;
                }
                for (int inA : aCloneMorf.elements) {
                    try {
                        b.elements.stream().filter(data -> Objects.equals(data, inA)).findFirst().get();
                    } catch (Exception e) {
                        result = false;
                    }
                }
            }

            if (result && i != 0) {
                System.out.println("Отображение сюръективно");
                if (injectiveMapping) {
                    System.out.println("Отображение биективно");
                }
                return true;
            } else if (result) {
                injectiveMapping = true;
                System.out.println("Отображение инъективно");
            }
        }
        return injectiveMapping;
    }

    public static boolean checkMorphism(@NotNull Algebra a, @NotNull Algebra b) {
        if (!checkMapping(a, b)) {
            return false;
        }
        int result0 = 0;
        boolean temp1 = false;
        for (String s : a.operations) {
            switch (s) {
                case "+":
                    for (int e : a.elements) {
                        if (temp1) {
                            result0 += e;
                        } else {
                            result0 = e;
                            temp1 = true;
                        }
                        ;
                    }
                    ;
                    break;
                case "-":
                    for (int e : a.elements) {
                        if (temp1) {
                            result0 -= e;
                        } else {
                            result0 = e;
                            temp1 = true;
                        }
                    }
                    break;
                case "*":
                    for (int e : a.elements) {
                        if (temp1) {
                            result0 *= e;
                        } else {
                            result0 = e;
                            temp1 = true;
                        }
                    }
                    break;
                case "^":
                    for (int e : a.elements) {
                        if (temp1) {
                            result0 = result0 ^ e;
                        } else {
                            result0 = e;
                            temp1 = true;
                        }
                    }
                    break;
                case "/": {

                    for (int e : a.elements) {
                        if (temp1) {

                            if (e != 0) {
                                result0 += e;
                            } else {
                                System.out.println("Division by zero is not allowed.");
                            }
                        } else {
                            result0 = e;
                            temp1 = true;
                        }

                    }
                    break;
                }
                default:
                    System.out.println("Unsupported operation: " + s);
            }
        }
        double result1 = 0;
        double result2 = 0;
        double result3 = 0;
        boolean temp2 = false;
        for (String s : b.operations) {
            switch (s) {
                case "+":
                    for (int e : a.elements) {
                        if (temp1) {
                            result1 += Math.exp(e);
                            result2 += Math.log(e);
                            result3 += Math.pow(e, 2);
                        } else {
                            result1 = Math.exp(e);
                            result2 = Math.log(e);
                            result3 = Math.pow(e, 2);
                            temp2 = true;
                        }
                        ;
                    }
                    ;
                    break;
                case "-":
                    for (int e : a.elements) {
                        if (temp1) {
                            result1 -= Math.exp(e);
                            result2 -= Math.log(e);
                            result3 -= Math.pow(e, 2);
                        } else {
                            result1 = Math.exp(e);
                            result2 = Math.log(e);
                            result3 = Math.pow(e, 2);
                            temp2 = true;
                        }
                    }
                    break;
                case "*":
                    for (int e : a.elements) {
                        if (temp1) {
                            result1 *= Math.exp(e);
                            result2 *= Math.log(e);
                            result3 *= Math.pow(e, 2);
                        } else {
                            result1 = Math.exp(e);
                            result2 = Math.log(e);
                            result3 = Math.pow(e, 2);
                            temp2 = true;
                        }
                    }
                    break;
                case "^":
                    for (int e : a.elements) {
                        if (temp1) {
                            result1 = Math.pow(result1, Math.exp(e));
                            result2 = Math.pow(result1, Math.log(e));
                            result3 = Math.pow(result1, Math.pow(e, 2));
                        } else {
                            result1 = Math.exp(e);
                            result2 = Math.log(e);
                            result3 = Math.pow(e, 2);
                            temp2 = true;
                        }
                    }
                    break;
                case "/":
                    for (int e : a.elements) {
                        if (temp2) {
                            if (e != 0) {
                                result1 = result1 / Math.exp(e);
                                result2 = result2 / Math.log(e);
                                result3 = result3 / Math.pow(e, 2);
                            } else {
                                System.out.println("Division by zero is not allowed.");
                            }
                        } else {
                            result1 = Math.exp(e);
                            result2 = Math.log(e);
                            result3 = Math.pow(e, 2);
                            temp2 = true;
                        }

                    }
                    break;
                default:
                    System.out.println("Unsupported operation: " + s);
            }
        }
        return (Math.exp(result0) == result1 | Math.log(result0) == result2 | Math.pow(result0, 2) == result3);
    }

    public void showAlg() {
        for (Algebra e : this.arrayOfAlgebras) {
            System.out.println(e.name + ": " + e.elements + " " + e.operations + " " + e.neutralElements);
        }
    }

    public static void main(String[] args) {
        mainClass in = new mainClass();

        in.inputAlgebra("ONE1{1,2,3;*;1}");
        in.inputAlgebra("ONE2{1,2,3;+;0}");
        in.arrayOfAlgebras.get(0).showElement();
        in.arrayOfAlgebras.get(1).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println();

        // add
        in.inputAlgebra("A{3,9;*;1}");
        in.inputAlgebra("B{1,2;+;0}");
        in.addElement(in.arrayOfAlgebras.get(0), 21);
        in.addElement(in.arrayOfAlgebras.get(1), 3);
        in.arrayOfAlgebras.get(0).showElement();
        in.arrayOfAlgebras.get(1).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println();

        // remove
        in.addElement(in.arrayOfAlgebras.get(1), 30);
        in.arrayOfAlgebras.get(0).showElement();
        in.arrayOfAlgebras.get(1).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println();

        in.removeElement(in.arrayOfAlgebras.get(1), 30);
        in.arrayOfAlgebras.get(0).showElement();
        in.arrayOfAlgebras.get(1).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(0), in.arrayOfAlgebras.get(1)));
        System.out.println();


        // morphism
        in.inputAlgebra("C{1,2,3;+;0}");
        in.inputAlgebra("D{1,2,3;*;1}");
        in.arrayOfAlgebras.get(2).showElement();
        in.arrayOfAlgebras.get(3).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(2), in.arrayOfAlgebras.get(3)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(2), in.arrayOfAlgebras.get(3)));
        System.out.println();

        in.inputAlgebra("C{3,9,21;*;1}");
        in.inputAlgebra("D{1,2,3;+;0}");
        in.arrayOfAlgebras.get(4).showElement();
        in.arrayOfAlgebras.get(5).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(4), in.arrayOfAlgebras.get(5)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(4), in.arrayOfAlgebras.get(5)));
        System.out.println();

        in.inputAlgebra("E{3,9,21;*;1}");
        in.inputAlgebra("F{1,2,7;+;0}");
        in.arrayOfAlgebras.get(6).showElement();
        in.arrayOfAlgebras.get(7).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(6), in.arrayOfAlgebras.get(7)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(6), in.arrayOfAlgebras.get(7)));
        System.out.println();

        in.inputAlgebra("ONE1{2;+;0}");
        in.inputAlgebra("ONE2{2;+;1}");
        in.arrayOfAlgebras.get(8).showElement();
        in.arrayOfAlgebras.get(9).showElement();
        System.out.println("checkMapping: " + checkMapping(in.arrayOfAlgebras.get(8), in.arrayOfAlgebras.get(9)));
        System.out.println("Morphism: " + checkMorphism(in.arrayOfAlgebras.get(8), in.arrayOfAlgebras.get(9)));
        System.out.println();

        // clone
        in.cloneElement(in.arrayOfAlgebras.get(5));
        in.showAlg();
        System.out.println();

        // expression
        String expression = in.buildExpression(5, "*", 13);
        System.out.println("Expression: " + expression);
        System.out.println("Reduced Result: " + in.reduceExpression(expression));
        System.out.println();

        System.out.println("Power of A: " + reduceElement(in.arrayOfAlgebras.getFirst()));
    }
}
