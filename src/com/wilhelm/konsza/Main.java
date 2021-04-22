package com.wilhelm.konsza;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // write your code here
        List<Employee> employees = new ArrayList<>();

        Employee john = new Employee("John Doe", 30);
        Employee tim = new Employee("Tim Buchalka", 21);
        Employee vili = new Employee("Wilhelm Konsza", 25);
        Employee snow = new Employee("John Snow", 100);
        Employee red = new Employee("Red RidingHood", 35);
        Employee charming = new Employee("Prince Charming", 45);

        employees.add(john);
        employees.add(tim);
        employees.add(vili);
        employees.add(snow);
        employees.add(red);
        employees.add(charming);

        Function<Employee, String> getLastName = employee ->
                employee.getName().substring(employee.getName().indexOf(' ') + 1);

        Function<Employee, String> getFirstName = employee ->
                employee.getName().substring(0, employee.getName().indexOf(' '));


        //employees.forEach(employee -> System.out.println(employee.getName().substring(employee.getName().indexOf(' ') + 1)));
        employees.forEach(employee -> System.out.println(getLastName.apply(employee)));
        System.out.println("\nFirst names: ");
        employees.forEach(employee -> System.out.println(getFirstName.apply(employee)));

        //Function chaining
        //uppercase gets entire name and converts it to upper case
        Function<Employee, String> upperCase = employee ->
                employee.getName().toUpperCase();

        //firstName function returns only the first name from the uppercase full name
        Function<String, String> firstName = name ->
                name.substring(0, name.indexOf(' '));

        System.out.println("=================================================");
        employees.forEach(employee -> {
            System.out.println(upperCase.andThen(firstName).apply(employee));
        });

        /*there are versions of the interfaces that accepts two arguments "Bi",
        for example the BiFunction interface
         */

        BiFunction<String, Employee, String> concatAge = (String name, Employee employee)
                -> name.concat(" " + employee.getAge());

        String upperName = upperCase.apply(employees.get(0));
        System.out.println(concatAge.apply("BiFunction result: "
                + upperName, employees.get(0)));


        /*UnaryOperator Interface -> accepts a single argument and returns a value
         * of the same type, can be chained just like Functions as it extends the
         * Functions Interface*/

        System.out.println("\nUnary operators: ");
        IntUnaryOperator intBy5 = i -> i * 5;
        System.out.println(intBy5.applyAsInt(10) + "" +
                "\n");

        /*Consumers can be chained together as well with the andThenMethod ini which
         * case each consumer will work on the value passed to the accept method*/

        Consumer<String> consumer = s -> s.toUpperCase();
        Consumer<String> consumer1 = s -> System.out.println(s);
        consumer.andThen(consumer1).accept("Hello world!");

        /*
        employees.forEach(System.out::println);
        An instance of the anonymous class is created (in effect, via the invoke dynamic operation)
        forEach is called, passing in that reference
        forEach's code calls accept repeatedly, each time passing one of the list items into it (which it receives as i)
        employees.forEach(employee -> System.out.println(employee));
        */

        employees.forEach(new Consumer<Employee>() {
            @Override
            public void accept(Employee employee) {
                System.out.println(employee);
            }
        });


        System.out.println("\nEmployees over 30: ");
        List<Employee> overThirty = employees.stream().filter(employee -> employee.getAge() > 30).collect(Collectors.toList());
        overThirty.forEach(System.out::println);

        System.out.println();
        employees.forEach(employee -> {
            if (employee.getAge() > 30) {
                System.out.println(employee);
            }
        });

        System.out.println("\nEmployees 30 and younger: ");
        System.out.println();
        employees.forEach(employee -> {
            if (employee.getAge() >= 30) {
                System.out.println(employee);
            }
        });

        printEmployeesByAga(employees, age -> age > 30);
        printEmployeesByAga(employees, age -> age <= 30);
        System.out.println("\nAnonymous Predicate interface");
        printEmployeesByAga(employees, new IntPredicate() {
                    @Override
                    public boolean test(int value) {
                        if (value > 30) {
                            return true;
                        }
                        return false;
                    }
                }
        );

        IntPredicate greaterThanFifteen = i -> i > 15;
        System.out.println(greaterThanFifteen.test(10));

        //Predicates can also be chained together (we can create a second predicate and chain it to the first one)
        IntPredicate lessThanOneHundred = i -> i < 100;
        System.out.println(greaterThanFifteen.and(lessThanOneHundred).test(25));

        Random random = new Random();
        Supplier<Integer> randomSupplier = () -> random.nextInt(1000);
        int counter = 10;
        while (counter >= 0) {
            System.out.println(randomSupplier.get());
            counter--;
        }

        employees.forEach(employee -> {
            String lastName = employee.getName().substring(employee.getName().indexOf(' ') + 1);
            System.out.println("Last name is: " + lastName);
        });

    }

    private static void printEmployeesByAga(List<Employee> employees, IntPredicate ageCondition) {
        System.out.println("\nprintEmployeesByAge method \n");
        employees.forEach(employee -> {
            if (ageCondition.test(employee.getAge())) {
                System.out.println(employee);
            }
        });
    }
}

