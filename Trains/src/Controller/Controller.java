package Controller;

import GraphicsInterface.*;
import Passenger.Passenger;
import Route.Route;
import Station.Station;
import Train.Train;
import javax.swing.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import static java.time.LocalDateTime.now;

/**
 * Created by Sega on 14.12.2016.
 */
public class Controller {
    public static ArrayList<Station> stations = new ArrayList<>();
    public static ArrayList<Route> routes = new ArrayList<>();
    public static ArrayList<Train> trains = new ArrayList<>();
    public static ArrayList<Passenger> passengers = new ArrayList<>();

    static Station station;
    static Route route;
    static Train train;
    static Passenger passenger;

    public static void createRoute(String name){
        route = new Route();
        route.addRoute(name);
        routes.add(route);
    }

    public static void createStation(String name, int x, int y){
        station = new Station();
        station.addStation(name, x, y);
        stations.add(station);
    }

    public static void createTrain(String name,Route route, int capacity, int price, LocalDateTime timeStart, int velocity){
        train = new Train();
        train.addTrain(name, route, capacity, price, timeStart, velocity);
        train.timeEnd = train.timeStart.plusSeconds(route.totalLength/train.velocity);
        trains.add(train);
        route.trainList.add(train);
    }

    public static void createPassenger(String fullName, Train train){
        passenger = new Passenger();
        passenger.addPassenger(fullName, train);
        train.passengerList.add(passenger);
        passengers.add(passenger);
    }

    public static void deleteStation(Station station){
        for (int i = 0; i < stations.size(); i++) {
            if (station.equals(stations.get(i)) ){
                stations.remove(i);
                break;
            }
        }
    }

    public static void deleteRoute(Route route){
        for (int i = 0; i < routes.size(); i++) {
            if (route.equals(routes.get(i)) ) {
                routes.remove(i);
                break;
            }
        }
    }

    public static void deleteTrain(Train train){
        for (int i = 0; i < trains.size(); i++) {
            if (train.equals(trains.get(i))) {
                trains.remove(i);
//                for (int j = i; j < trains.size(); j++) {
//                    train.indexOfRoute--;
//                }
                break;
            }
        }
    }

    public static void deletePassenger(Passenger passenger){
        for (int i = 0; i < trains.size(); i++)
            if (trains.get(i).equals(passenger.train)){
                trains.get(i).passengerList.remove(passenger);
                passengers.remove(passenger);
                break;
            }
    }

    //Заполняем карту
    static void createData(){
        LocalDateTime localDateTime = LocalDateTime.of(now().getYear(), now().getMonth(), now().getDayOfMonth(), now().getHour(), now().getMinute(), now().getSecond());

        createStation("Moscow",50,200);
        createStation("Nizhniy Novgorod",250,180);
        createStation("Cheboksary",370,190);
        createStation("Kazan",445,200);
        createStation("Saint Petersburg",30,30);

        createRoute("Moscow - Kazan");
        route.addStationToRoute(stations.get(0));
        route.addStationToRoute(stations.get(1));
        route.addStationToRoute(stations.get(2));
        route.addStationToRoute(stations.get(3));
        createTrain("A96", route, 300, 1200, localDateTime, 20);

        createRoute("Moscow - Saint Petersburg");
        route.addStationToRoute(stations.get(4));
        route.addStationToRoute(stations.get(0));
        createTrain("B64", route, 400, 1800, localDateTime.plusSeconds(5),10);
        createTrain("C46", route, 400, 1800, localDateTime.plusSeconds(10),6);

        createPassenger("Василий Петрович", trains.get(0));
        createPassenger("Оби-ван-Кеноби", trains.get(1));
        createPassenger("Питер Паркер", trains.get(1));
        createPassenger("Том Харди", trains.get(1));
        createPassenger("Илон Маск", trains.get(2));
    }

    //Вывод данных в консоль
    static void output(){
        System.out.println("Список станций");
        for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i).getStation());
        }
        System.out.println();

        System.out.println("Список поездов");
        for (int i = 0; i < trains.size(); i++) {
            System.out.println(trains.get(i).getTrain());
        }
        System.out.println();

        System.out.println("Список пассажиров");
        for (int i = 0; i < passengers.size(); i++) {
            System.out.println(passengers.get(i).getPassenger());
        }
        System.out.println();

        System.out.println("Список маршрутов");
        for (int i = 0; i < routes.size() ; i++) {
            System.out.println();
            System.out.println("Название маршрута");
            System.out.println(routes.get(i).name);
            System.out.println("Список поездов на маршруте");
            System.out.println(routes.get(i).getTrainList());
            System.out.println("Список станций");
            System.out.println(routes.get(i).getStationList());
            System.out.println("Расстояния между станциями");
            System.out.println(routes.get(i).getLengths());
        }
    }

    public static void main(String[] args) {
        createData();
        //output();

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GraphicInterface();
            }
        });
    }
}
/*
* Добавить паттерны
* Добавить руководство
* */