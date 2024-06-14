package controller;

import enam.Role;
import enam.Status;
import model.Project;
import model.Task;
import model.User;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Objects;

import static controller.Main.*;


public class ManagerController {
    public static void managerController() {
        System.out.println("1 CRUD Project \t 2 Add Employee \t 3 Show Employer \t 4 Delete Employer \t5 CRUD Task \t 6 Assign Task \t 0 Exit");
        String command = inputStr("Choose -> ");
        switch (command) {
            case "1" -> projectMenu();
            case "2" -> addEmployer();
            case "3" -> showEmployer();
            case "4" -> deleteEmployer();
            case "5" -> taskMenu();
            case "6" -> assignTask();
            case "0" -> UserController.signIn();
            default -> managerController();
        }
    }

    public static void assignTask() {
        ArrayList<Task> tasks = TaskController.read();
        if (tasks.isEmpty()) {
            managerController();
        }
        try {
            System.out.print("0.Exit\tEnter choice: ");
            int choice = scanInt.nextInt() - 1;
            if (choice == -1) {
                managerController();
            }
            ArrayList<User> users = userService.getNoWorkingEmployerProjects(tasks.get(choice).getProjectId());
            int i = 1;
            for (User user : users) {
                System.out.println(i++ + ". " + user.getUsername() + "  " + user.getRole());
            }
            System.out.print("0.Exit\tEnter choice: ");
            int ans = scanInt.nextInt() - 1;
            if (ans == -1) {
                managerController();
            }
            User employee = users.get(ans);
            Task task = tasks.get(choice);
            task.setStatus(Status.IN_PROGRESS);
            task.setEmployerId(employee.getId());
            taskService.update(task.getId(), task);
            System.out.println("Successfully ✅");

        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    private static void deleteEmployer() {
        ArrayList<User> managers = showEmployer();

        try {
            System.out.print("0.Exit\tChoose one: ");
            int choose = scanInt.nextInt() - 1;
            if (choose == -1) {
                managerController();
            }
            taskService.deleteTaskForEmployer(managers.get(choose).getId());
            userService.delete(managers.get(choose).getId());
            System.out.println("Delete Successfully ✅");
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }

    }

    private static ArrayList<User> showEmployer() {
        ArrayList<Project> projects = ProjectController.read();
        if (projects.isEmpty()) {
            managerController();
        }
        ArrayList<User> users = null;
        try {
            System.out.print("0.Exit\tChoose one: ");
            int choose = scanInt.nextInt() - 1;
            if (choose == -1) {
                managerController();
            }
            users = userService.getEmployerProjects(projects.get(choose).getId());
            int i = 1;
            for (User user : users) {
                System.out.println(i++ + "." + user);
            }
        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
        if (Objects.isNull(users)) {
            managerController();
        }

        return users;
    }


    private static void addEmployer() {
        ArrayList<Project> projects = ProjectController.read();
        if (projects.isEmpty()) {
            managerController();
        }
        try {
            System.out.print("0.Exit\tEnter choice: ");
            int choose = scanInt.nextInt() - 1;
            if (choose == -1) {
                managerController();
            }

            String username = inputStr("Enter username : ");
            String password = inputStr("Enter password : ");

            if (userService.add(new User(username, password, projects.get(choose).getId(), getRole()))) {
                System.out.println("Employer Successfully Added ✅");
                managerController();
            } else {
                System.out.println("Please try Again ♻️");
                managerController();
            }


        } catch (InputMismatchException | IndexOutOfBoundsException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void projectMenu() {
        while (true) {
            System.out.println("1.Create\t2.Read\t3.Update\t4.Delete\t0.Exit");
            String choice = scanStr.nextLine();
            switch (choice) {
                case "1" -> ProjectController.create();
                case "2" -> ProjectController.read();
                case "3" -> ProjectController.update();
                case "4" -> ProjectController.delete();
                case "0" -> managerController();
                default -> projectMenu();
            }
        }
    }

    public static Role taskMenu() {
        while (true) {
            System.out.println("1.Create\t2.Read\t3.Update\t4.Delete\t0.Exit");
            String choice = scanStr.nextLine();
            switch (choice) {
                case "1" -> TaskController.create();
                case "2" -> TaskController.read();
                case "3" -> TaskController.update();
                case "4" -> TaskController.delete();
                case "0" -> managerController();
                default -> taskMenu();
            }
        }
    }


    static Role getRole() {
        Role role = null;
        System.out.println("1 TEAM_LEAD_BE\t2 TEAM_LEAD_FE\t3 TESTER\t 4 DEVELOPER_BE\t5 DEVELOPER_FE\t0.Exit");
        String command = inputStr("Choose employer-> ");
        switch (command) {
            case "1" -> role = Role.TEAM_LEAD_BE;
            case "2" -> role = Role.TEAM_LEAD_F;
            case "3" -> role = Role.TESTER;
            case "4" -> role = Role.DEVELOPER_BE;
            case "5" -> role = Role.DEVELOPER_FE;
            case "0" -> managerController();
            default -> getRole();
        }

        return role;


    }
}

