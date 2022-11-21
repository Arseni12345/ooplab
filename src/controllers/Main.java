package controllers;

import UI.TabulatedFunctionDoc;
import controllers.FXMLMainFormController;
import functions.FunctionPoint;
import functions.Functions;
import functions.basic.Log;
import functions.basic.trigonometricfunctions.Sin;
import functions.exception.InappropriateFunctionPointException;
import functions.tabbulatedfunctions.ArrayTabulatedFunction;
import functions.tabbulatedfunctions.LinkedListTabulatedFunction;
import functions.tabbulatedfunctions.TabulatedFunction;
import functions.tabbulatedfunctions.TabulatedFunctions;
import javafx.application.Application;
import javafx.stage.*;
import javafx.fxml.*;
import javafx.scene.*;
import threads.SimpleGenerator;
import threads.SimpleIntegrator;
import threads.Task;

import java.util.concurrent.Semaphore;

public class Main extends Application {
    public static void main(String[] args) {
        TabulatedFunction tabulatedFunction = null;
        try {
            tabulatedFunction = new LinkedListTabulatedFunction(-10, 20, 30);
            for (FunctionPoint point : tabulatedFunction) {
                System.out.println(point.getX());
            }
            TabulatedFunction f;
            f = TabulatedFunctions.createTabulatedFunction(
                    ArrayTabulatedFunction.class, 0, 10, 3);
            System.out.println(f.getClass());
            System.out.println(f);
            f = TabulatedFunctions.createTabulatedFunction(
                    ArrayTabulatedFunction.class, 0, 10, new double[]{0, 10});
            System.out.println(f.getClass());
            System.out.println(f);
            f = TabulatedFunctions.createTabulatedFunction(
                    LinkedListTabulatedFunction.class,
                    new FunctionPoint[]{
                            new FunctionPoint(0, 0),
                            new FunctionPoint(10, 10)
                    }
            );
            System.out.println(f.getClass());
            System.out.println(f);
            f = TabulatedFunctions.tabulate(
                    LinkedListTabulatedFunction.class, new Sin(), 0, Math.PI, 11);
            System.out.println(f.getClass());
            System.out.println(f);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        launch(args);
    }
    public static TabulatedFunctionDoc tabFDoc;

    @Override
    public void start(Stage stage) throws Exception {
        simpleThreads();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLMainForm.fxml"));
        Parent root = loader.load();
        FXMLMainFormController ctrl = loader.getController();
        ctrl.setStage(stage);
        tabFDoc.registerRedrawFunctionController(ctrl);
        Scene scene = new Scene(root);
        stage.setTitle("Tabulated function app");
        stage.setScene(scene);
        stage.show();
    }

    public void init() throws Exception {
        super.init();
        if (tabFDoc == null) {
            tabFDoc = new TabulatedFunctionDoc();
            tabFDoc.newFunction(0, 3, 4);
        }
    }

    public void nonTread(){
        for(int i = 0; i < 100; ++i){
            Task task = new Task();
            task.setFunction(new Log(Math.random()*9 + 1));
            task.setLeftDomain(Math.random()*100);
            task.setSamplingStep(Math.random());
            task.setRightDomain(Math.random()*100 + 100);
            System.out.println("Source: " + task.getLeftDomain() + " " + task.getRightDomain() + " " + task.getSamplingStep());
            try {
                System.out.println("Result: " + task.getLeftDomain() + " " + task.getRightDomain() + " " + task.getSamplingStep() + " " + Functions.integrate(task.getFunction(), task.getLeftDomain(), task.getRightDomain(), task.getSamplingStep()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void simpleThreads() throws InterruptedException {
        Semaphore semaphore = new Semaphore(1);
        Task task = new Task();

        Thread generate = new Thread(new SimpleGenerator(task, semaphore));
        Thread integrate = new Thread(new SimpleIntegrator(task, semaphore));

        generate.start();
        integrate.start();

        generate.join();
        integrate.join();

        long time = System.currentTimeMillis();
        while(System.currentTimeMillis() - time != 50);
        generate.interrupt();
        integrate.interrupt();
    }
}
