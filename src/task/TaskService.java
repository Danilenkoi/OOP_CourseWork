package task;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Collectors;
import type.*;
import info.Information;

public class TaskService {
    static Scanner in = new Scanner(System.in);

    public void mainWindow(Map<Integer, Information> mapOfTasks, List<Information> removedTasks) {

        boolean isExit = false;
        do {
            System.out.println("""
                     \nГЛАВНОЕ МЕНЮ ЕЖЕДНЕВНИКА
                     1 - Добавить задачу
                     2 - Получить задачи на текущий день
                     3 - Редактировать задачу по id
                     4 - Получить задачи на дату
                     5 - Получить все задачи, упорядоченные по датам
                     6 - Удалить задачу по id
                     7 - Показать список удаленных задач
                     -----------------------------------
                     8 - Выход из программы
                     """);
            System.out.print("Выберите нужный вариант : ");
            String choice = in.nextLine();
            switch (choice) {
                case "1" -> newTask(mapOfTasks);
                case "2" -> showCurrentTasks(mapOfTasks);
                case "3" -> editTask(mapOfTasks);
                case "4" -> showTasksOfDate(mapOfTasks);
                case "5" -> showAllTasksByDate(mapOfTasks);
                case "6" -> deleteTask(mapOfTasks, removedTasks);
                case "7" -> showDeletedTasks(removedTasks);
                case "8" -> isExit = true;
                default -> System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (!isExit);
    }

    public void newTask(Map<Integer, Information> mapOfTasks) {
        Type type = null;
        String choice;
        do {
            System.out.print("""
                     Меню выбора типа задачи
                     1 - Личная
                     2 - Рабочая
                     Выберите тип задачи:\s""");
            choice = in.nextLine();
            switch (choice) {
                case "1" -> type = Type.PERSONAL;
                case "2" -> type = Type.WORK;
                default -> System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (!(choice.equals("1") || choice.equals("2")));

        LocalDateTime dateTime = null;
        do {
            System.out.print("Задайте дату и время выполнения задачи в формате 'дд.мм.гггг чч:мм': ");
            try {
                dateTime = LocalDateTime.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));
            } catch (DateTimeParseException e) {
                System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (Objects.equals(dateTime, null));

        System.out.print("Введите название задачи: ");
        String title = in.nextLine();

        System.out.print("Введите описание задачи: ");
        String description = in.nextLine();

        do {
            System.out.print("""
                     Периодичность выполнения задачи
                     1 - Однократно
                     2 - Ежедневно
                     3 - Еженедельно
                     4 - Ежемесячно
                     5 - Ежегодно
                     Выберите нужный вариант:\s""");
            choice = in.nextLine();
            switch (choice) {
                case "1" -> makeChoice(new OneTimeTask(type, title, dateTime, description), mapOfTasks);
                case "2" -> makeChoice(new DailyTask(type, title, dateTime, description), mapOfTasks);
                case "3" -> makeChoice(new WeeklyTask(type, title, dateTime, description), mapOfTasks);
                case "4" -> makeChoice(new MonthlyTask(type, title, dateTime, description), mapOfTasks);
                case "5" -> makeChoice(new YearlyTask(type, title, dateTime, description), mapOfTasks);
                default -> System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (!(choice.equals("1") || choice.equals("2") || choice.equals("3") || choice.equals("4") || choice.equals("5")));
    }

    public void makeChoice(Task task, Map<Integer, Information> mapOfTasks) {
        mapOfTasks.put(task.getId(), (Information) task);
    }

    public void showCurrentTasks(Map<Integer, Information> mapOfTasks) {
        mapOfTasks.entrySet().stream()
                .filter(task -> task.getValue().appearsIn(LocalDate.now()))
                .forEach(s -> System.out.println(s.getValue()));
    }

    public void editTask(Map<Integer, Information> mapOfTasks) {
        int id = -1;
        do {
            System.out.print("Введите значение id для удаление задачи: ");
            try {
                id = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (id == -1);
        if (!mapOfTasks.containsKey(id)) {
            System.out.println("Такого id не существует");
        } else {
            System.out.println("Текущее название задачи: " + mapOfTasks.get(id).getTitleFromTask());
            System.out.print("Введите новое название: ");
            mapOfTasks.get(id).setTitleFromTask(in.nextLine());
            System.out.println("Текущее описание задачи: " + mapOfTasks.get(id).getDescriptionFromTask());
            System.out.print("Введите новое описание: ");
            mapOfTasks.get(id).setDescriptionFromTask(in.nextLine());
            System.out.println("Задача успешно изменена");
        }
    }

    public void showTasksOfDate(Map<Integer, Information> mapOfTasks) {
        LocalDate date = null;
        do {
            System.out.print("Задайте дату для просмотра задач в формате 'дд.мм.гггг': ");
            try {
                date = LocalDate.parse(in.nextLine(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
            } catch (DateTimeParseException e) {
                System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (Objects.equals(date, null));
        LocalDate finalDate = date;
        if (mapOfTasks.entrySet().stream().noneMatch(task -> task.getValue().appearsIn(finalDate))) {
            System.out.println("На эту дату нет задач");
        } else {
            mapOfTasks.entrySet().stream()
                    .filter(task -> task.getValue().appearsIn(finalDate))
                    .forEach(s -> System.out.println(s.getValue()));
        }
    }

    public void showAllTasksByDate(Map<Integer, Information> mapOfTasks) {
        Map<Integer, Information> sortedByDate = mapOfTasks.entrySet().stream()
                .sorted(Comparator.comparing(word -> word.getValue().getDateFromTask()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
        sortedByDate.forEach((key, value) -> System.out.println(value));
    }


    public void deleteTask(Map<Integer, Information> mapOfTasks, List<Information> removedTasks) {
        int id = -1;
        do {
            System.out.print("Введите значение id для удаление задачи: ");
            try {
                id = Integer.parseInt(in.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("\nНеправильный ввод, повторите\n");
            }
        } while (id == -1);
        if (mapOfTasks.containsKey(id)) {
            removedTasks.add(mapOfTasks.get(id));
            mapOfTasks.remove(id);
        } else System.out.println("Такого id не существует");
    }

    public void showDeletedTasks(List<Information> removedTasks) {
        System.out.println("Архив удаленных задач");
        System.out.println(removedTasks);
    }
}
