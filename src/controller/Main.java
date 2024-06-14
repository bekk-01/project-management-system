package controller;

import enam.Role;
import model.User;
import repository.ProjectRepository;
import repository.UserRepository;
import service.ProjectService;
import service.TaskService;
import service.UserService;

import java.util.Scanner;

import static controller.ProjectController.*;


public class Main {
    public static Scanner scanStr = new Scanner(System.in);
    public static Scanner scanInt = new Scanner(System.in);

    public static UserService userService = UserService.getInstance();
    public static ProjectService projectService = ProjectService.getInstance();

    public static TaskService taskService = TaskService.getInstance();
    public static User curretnUser = null;


    public static void main(String[] args) {
        UserController.signIn();

    }

    static {
        userService.add(new User("1", "1", null, Role.SUPER_ADMIN));
        userService.add(new User("2", "2", null, Role.ADMIN));
        userService.add(new User("3", "3", null, Role.MANAGER));
    }

    public static String inputStr(String hint) {
        System.out.print(hint);
        return scanStr.nextLine();
    }

    public static int inputInt(String hint) {
        System.out.print(hint);
        return scanInt.nextInt();
    }
}