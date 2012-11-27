package lanit.junit.report.templates.helper;

import java.util.*;

public class ParserAutotestLog {

    private static HashMap<String, String> stackTraces = new HashMap<String, String>();
    private static HashMap<String, String> screenShots = new HashMap<String, String>();
    public static String htmlResultInfo = "";
    public static String htmlListOfSteps = "";
    public static String htmlInfoOfSteps = "";

    /**
     * Method create html content left top part of report.htm into htmlResultInfo variable
     * @param prerequisitePart - array list of prerequisites log rows
     * @param mainPart - array list of main part of form testing log rows
     */
    public static void defineLeftTopPartData(ArrayList<String> prerequisitePart, ArrayList<String> mainPart) {
        String commonTestPlanCount = "0";
        int commonTestPlanPercent = 100;
        String correctTestPlanCount = "0";
        int correctTestPlanPercent = 0;
        String incorrectTestPlanCount = "0";
        int incorrectTestPlanPercent = 0;
        for (int i = 0; i < prerequisitePart.size(); i++) {
            if (prerequisitePart.get(i).contains("Количество тестовых планов на выполнение")) {
                commonTestPlanCount = prerequisitePart.get(i).substring(prerequisitePart.get(i).lastIndexOf(" ") + 1, prerequisitePart.get(i).length());
            }
        }
        int common = Integer.valueOf(commonTestPlanCount);
        int correct = Integer.valueOf(correctTestPlanCount);
        int incorrect = Integer.valueOf(incorrectTestPlanCount);
        for (int i = 0; i < mainPart.size(); i++) {
            if (mainPart.get(i).contains("Запуск тестового плана")) {
                for (i++; i < mainPart.size(); i++) {
                    if (mainPart.get(i).contains("ERROR")) {
                        incorrect++;
                        break;
                    }
                    if (mainPart.get(i).contains("Завершение тестового плана")) {
                        correct++;
                        break;
                    }
                }
            }
        }
        correctTestPlanCount = String.valueOf(correct);
        incorrectTestPlanCount = String.valueOf(incorrect);
        if (common != 0) {
            if (correct > 0) {
                correctTestPlanPercent = (correct * 100) / common;
                incorrectTestPlanPercent = commonTestPlanPercent - correctTestPlanPercent;
            } else {
                incorrectTestPlanPercent = (incorrect * 100) / common;
                correctTestPlanPercent = commonTestPlanPercent - incorrectTestPlanPercent;
            }
        }
        htmlResultInfo += "<ul>\n";
        htmlResultInfo += "<li style=\"color: #300000; font-weight: bolder;\">Общее количество форм для тестирования: " + commonTestPlanCount + " - " + commonTestPlanPercent + "%</li>\n";
        htmlResultInfo += "<li style=\"color: #006600; font-weight: bolder;\">Количество корректных форм: " + correctTestPlanCount + " - " + correctTestPlanPercent + "%</li>\n";
        htmlResultInfo += "<li style=\"color: #ff0000; font-weight: bolder;\">Количество некорректных форм: " + incorrectTestPlanCount + " - " + incorrectTestPlanPercent + "%</li>\n";
        htmlResultInfo += "</ul>\n";
    }

    /**
     * Method create html content left bottom part of report.htm into htmlListOfSteps variable
     * @param prerequisitePart - array list of prerequisites log rows
     * @param mainPart - array list of main part of form testing log rows
     * @param postrequisitePart - array list of post requisites log rows
     */
    public static void defineLeftBottomPartData(ArrayList<String> prerequisitePart, ArrayList<String> mainPart, ArrayList<String> postrequisitePart) {
        prerequisitePart = prepareArrayListOfLogs(prerequisitePart);
        mainPart = prepareArrayListOfLogs(mainPart);
        postrequisitePart = prepareArrayListOfLogs(postrequisitePart);
        //prepare first prerequisites html part
        htmlListOfSteps += "<ul id=\"steps\">\n";
        htmlListOfSteps += "<ul class=\"first\">\n";
        htmlListOfSteps += "<div class=\"first green\" onclick=\"listView(this)\">\n";
        htmlListOfSteps += "<a><text>— </text>Выполнение пререквизитов автотеста</a>\n";
        htmlListOfSteps += "</div>\n";
        for (int i = 0; i < prerequisitePart.size(); i++) {
            String row = prerequisitePart.get(i);
            if (row.contains("Запуск метода")) {
                htmlListOfSteps += "<ul class=\"second\">\n";
                htmlListOfSteps += "<div class=\"second\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";
                for (i++; i < prerequisitePart.size(); i++) {
                    row = prerequisitePart.get(i);
                    htmlListOfSteps += "<div class=\"third\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                    htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                    htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                    htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                    htmlListOfSteps += "</div>\n";
                    if (row.contains("Завершение метода")) {
                        break;
                    }
                }
                htmlListOfSteps += "</ul>\n";
            } else {
                htmlListOfSteps += "<div class=\"second\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";
            }
        }
        htmlListOfSteps += "</ul>\n";
        //prepare second main html part
        htmlListOfSteps += "<ul class=\"first\">\n";
        htmlListOfSteps += "<div class=\"first green\" onclick=\"listView(this)\">\n";
        htmlListOfSteps += "<a><text>— </text>Выполнение тестирования форм<a>\n";
        htmlListOfSteps += "</div>\n";
        for (int i = 0; i < mainPart.size(); i++) {
            String row = mainPart.get(i);
            if (row.contains("Запуск тестового плана")) {
                htmlListOfSteps += "<ul class=\"second\">\n";
                htmlListOfSteps += "<div class=\"second\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";
                for (i++; i < mainPart.size(); i++) {
                    row = mainPart.get(i);
                    if (row.contains("Запуск метода")) {
                        htmlListOfSteps += "<ul class=\"third\">\n";
                        htmlListOfSteps += "<div class=\"third\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                        htmlListOfSteps += "</div>\n";
                        for (i++; i < mainPart.size(); i++) {
                            row = mainPart.get(i);
                            if (row.contains("Шаг №")) {
                                htmlListOfSteps += "<ul class=\"forth\">\n";
                                htmlListOfSteps += "<div class=\"forth\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                htmlListOfSteps += "</div>\n";
                                for (i++; i < mainPart.size(); i++) {
                                    row = mainPart.get(i);
                                    if (row.contains("Заголовок шага")) {
                                        String shortRow = row.substring(row.indexOf("-") + 2, row.length());
                                        htmlListOfSteps += "<div class=\"fifth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + shortRow.substring(0, shortRow.indexOf(":")) + "</a>\n";
                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                        htmlListOfSteps += "</div>\n";
                                        if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода")) {
                                            break;
                                        }
                                    } else if (row.contains("Проверка структуры колонок")) {
                                        htmlListOfSteps += "<ul class=\"fifth\">\n";
                                        htmlListOfSteps += "<div class=\"fifth\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                        htmlListOfSteps += "</div>\n";
                                        for (i++; i < mainPart.size(); i++) {
                                            row = mainPart.get(i);
                                            if (row.contains("Строка")) {
                                                htmlListOfSteps += "<div class=\"sixth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                htmlListOfSteps += "</div>\n";
                                            }
                                            if (!mainPart.get(i + 1).contains("Строка") || mainPart.get(i + 1).contains("Завершение метода")) {
                                                break;
                                            }
                                        }
                                        htmlListOfSteps += "</ul>\n";
                                        if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода")) {
                                            break;
                                        }
                                    } else if (row.contains("Тестирование поля")) {
                                        htmlListOfSteps += "<ul class=\"fifth\">\n";
                                        htmlListOfSteps += "<div class=\"fifth\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                        htmlListOfSteps += "</div>\n";
                                        for (i++; i < mainPart.size(); i++) {
                                            row = mainPart.get(i);
                                            if (row.contains("Валидация ")) {
                                                htmlListOfSteps += "<ul class=\"sixth\">\n";
                                                htmlListOfSteps += "<div class=\"sixth\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                htmlListOfSteps += "</div>\n";
                                                for (i++; i < mainPart.size(); i++) {
                                                    row = mainPart.get(i);
                                                    htmlListOfSteps += "<div class=\"seventh\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                    htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                                    htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                    htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                    htmlListOfSteps += "</div>\n";
                                                    if (mainPart.get(i + 1).contains("Исследуемая комбинация") || mainPart.get(i + 1).contains("Завершение метода")) {
                                                        break;
                                                    }
                                                }
                                                htmlListOfSteps += "</ul>\n";
                                                if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода") || mainPart.get(i + 1).contains("Тестирование поля")) {
                                                    break;
                                                }
                                            } else if (row.contains("Исследуемая комбинация ")) {
                                                String shortRow = row.substring(row.indexOf("-") + 2, row.length());
                                                htmlListOfSteps += "<div class=\"sixth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + shortRow.substring(0, shortRow.indexOf(":")) + "</a>\n";
                                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                htmlListOfSteps += "</div>\n";
                                                if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода") || mainPart.get(i + 1).contains("Тестирование поля")) {
                                                    break;
                                                }
                                            } else if (row.substring(row.indexOf("-") + 2, row.length()).startsWith("Номер заявления")) {
                                                String rowRequestNumber = row.substring(row.indexOf("-") + 2, row.length());
                                                htmlListOfSteps += "<ul class=\"sixth\">\n";
                                                htmlListOfSteps += "<div class=\"sixth\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + rowRequestNumber.substring(0, rowRequestNumber.indexOf(".")) + "</a>\n";
                                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                htmlListOfSteps += "</div>\n";
                                                for (i++; i < mainPart.size(); i++) {
                                                    row = mainPart.get(i);
                                                    if (row.contains("Заявление с номером")) {
                                                        String rowRequestStatus = row.substring(row.indexOf("-") + 2, row.length());
                                                        htmlListOfSteps += "<div class=\"seventh\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + rowRequestStatus.substring(0, rowRequestStatus.indexOf(":")) + "</a>\n";
                                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                        htmlListOfSteps += "</div>\n";
                                                    } else {
                                                        htmlListOfSteps += "<div class=\"seventh\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                        htmlListOfSteps += "</div>\n";
                                                    }
                                                    if (!mainPart.get(i + 1).contains("Заявление с ")) {
                                                        break;
                                                    }
                                                }
                                                htmlListOfSteps += "</ul>\n";
                                                if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода") || mainPart.get(i + 1).contains("Тестирование поля")) {
                                                    break;
                                                }
                                            } else {
                                                htmlListOfSteps += "<div class=\"sixth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                                htmlListOfSteps += "</div>\n";
                                                if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода") || mainPart.get(i + 1).contains("Тестирование поля")) {
                                                    break;
                                                }
                                            }
                                        }
                                        htmlListOfSteps += "</ul>\n";
                                        if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода")) {
                                            break;
                                        }
                                    } else {
                                        if (row.contains("Завершение метода")) {
                                            break;
                                        }
                                        htmlListOfSteps += "<div class=\"fifth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                        htmlListOfSteps += "</div>\n";
                                        if (mainPart.get(i + 1).contains("Шаг №") || mainPart.get(i + 1).contains("Завершение метода")) {
                                            break;
                                        }
                                    }
                                }
                                htmlListOfSteps += "</ul>\n";
                            } else {
                                htmlListOfSteps += "<div class=\"forth\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                                htmlListOfSteps += "</div>\n";
                                if (row.contains("Завершение метода") || row.contains("Завершение тестового плана")) {
                                    break;
                                }
                            }
                        }
                        htmlListOfSteps += "</ul>\n";
                    } else {
                        htmlListOfSteps += "<div class=\"third\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                        htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                        htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                        htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                        htmlListOfSteps += "</div>\n";
                    }
                    if (row.contains("Завершение тестового плана")) {
                        break;
                    }
                }
                htmlListOfSteps += "</ul>\n";
            } else {
                htmlListOfSteps += "<div class=\"second\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";
            }
        }
        htmlListOfSteps += "</ul>\n";
        //prepare third postrequisites html part
        htmlListOfSteps += "<ul class=\"first\">\n";
        htmlListOfSteps += "<div class=\"first green\" onclick=\"listView(this)\">\n";
        htmlListOfSteps += "<a><text>— </text>Выполнение постреквизитов автотеста</a>\n";
        htmlListOfSteps += "</div>\n";
        for (int i = 0; i < postrequisitePart.size(); i++) {
            String row = postrequisitePart.get(i);
            if (row.contains("Запуск метода")) {
                htmlListOfSteps += "<ul class=\"second\">\n";
                htmlListOfSteps += "<div class=\"second\" onclick=\"listView(this)\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\"><text>— </text>" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";
                for (i++; i < postrequisitePart.size(); i++) {
                    row = postrequisitePart.get(i);
                    htmlListOfSteps += "<div class=\"third\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                    htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                    htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                    htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                    htmlListOfSteps += "</div>\n";
                    if (row.contains("Завершение метода")) {
                        break;
                    }
                }
                htmlListOfSteps += "</ul>\n";
            } else {
                htmlListOfSteps += "<div class=\"second\" title=\"" + row.substring(13, row.indexOf(" ", 14)) + "\">\n";
                htmlListOfSteps += "<a onclick=\"showInfo(this, '" + row + "')\">" + row.substring(row.indexOf("-") + 2, row.length()) + "</a>\n";
                htmlListOfSteps += "<span class=\"stacktrace\">" + haveStackTrace(row) + "</span>\n";
                htmlListOfSteps += "<span class=\"screenshot\">" + haveScreenShot(row) + "</span>\n";
                htmlListOfSteps += "</div>\n";

            }
        }
        htmlListOfSteps += "</ul>\n";
        htmlListOfSteps += "</ul>\n";
    }

    /**
     * Searching screen shot existing for current log row into screenShots hash map
     * @param key - current log row
     * @return html content about screen shot existing
     */
    private static String haveScreenShot(String key) {
        return screenShots.get(key) != null ? "<a class=\"currentscreen\" onclick=showScreen('" + screenShots.get(key) + "')>просмотр</a>" : "отсутствует";
    }

    /**
     * Searching stack trace existing for current log row into stackTraces hash map
     * @param key - current log row
     * @return html content about stack trace existing
     */
    private static String haveStackTrace(String key) {
        return stackTraces.get(key) != null ? stackTraces.get(key) : "отсутствует";
    }

    /**
     * Preparing prerequisitePart, mainPart and postrequisitePart array lists for writable to report.htm
     * @param arrayListOfLogs - current array list with log rows
     * @return preparing array list with log rows
     */
    private static ArrayList<String> prepareArrayListOfLogs(ArrayList<String> arrayListOfLogs) {
        //remove all quotes from logs
        arrayListOfLogs = removeQuotes(arrayListOfLogs);
        //save all stack traces to HashMap
        saveStackTraces(stackTraces, arrayListOfLogs);
        //remove all stack traces from logs
        arrayListOfLogs = removeStackTrace(arrayListOfLogs);
        //save all screen shots to HashMap
        saveScreenShots(screenShots, arrayListOfLogs);
        //remove all screen shots from logs
        arrayListOfLogs = removeScreenShot(arrayListOfLogs);
        return arrayListOfLogs;
    }

    /**
     * Searching screen shot into log rows and save it to screenShots hash map
     * @param screenShots - screenShots hash map
     * @param htmlPart - array list with log rows
     */
    private static void saveScreenShots(HashMap<String, String> screenShots, ArrayList<String> htmlPart) {
        for (int i = 0; i < htmlPart.size(); i++) {
            if (htmlPart.get(i).contains(" ScreenShot: ")) {
                String key = htmlPart.get(i - 1);
                String value = htmlPart.get(i).substring(htmlPart.get(i).indexOf("ScreenShot:") + 12, htmlPart.get(i).length());
                screenShots.put(key, value);
            }
        }
    }

    /**
     * Removing log rows about screen shot from array list
     * @param arrayListOfLogs - array list with log rows
     * @return array list with log rows without screen shot information
     */
    private static ArrayList<String> removeScreenShot(ArrayList<String> arrayListOfLogs) {
        ArrayList<String> newArrayListOfLogs = new ArrayList<String>();
        for (int i = 0; i < arrayListOfLogs.size(); i++) {
            if (!arrayListOfLogs.get(i).contains(" ScreenShot: ")) {
                newArrayListOfLogs.add(arrayListOfLogs.get(i));
            }
        }
        return newArrayListOfLogs;
    }

    /**
     * Searching stack trace into log rows and save it to stackTraces hash map
     * @param stackTraces - stackTraces hash map
     * @param htmlPart - array list with log rows
     */
    private static void saveStackTraces(HashMap<String, String> stackTraces, ArrayList<String> htmlPart) {
        for (int i = 0; i < htmlPart.size(); i++) {
            if (htmlPart.get(i).contains(" ERROR ")) {
                String key = htmlPart.get(i);
                String value = "";
                for (int j = ++i; j < htmlPart.size(); j++) {
                    if (htmlPart.get(j).contains(" ERROR main ") ||
                            htmlPart.get(j).contains(" INFO main ") ||
                            htmlPart.get(j).contains(" WARN main ")) {
                        break;
                    } else {
                        value += htmlPart.get(j) + "\n";
                    }
                }
                stackTraces.put(key, value);
            }
        }
    }

    /**
     * Removing log rows about stack trace from array list
     * @param arrayListOfLogs - array list with log rows
     * @return array list with log rows without stack trace information
     */
    private static ArrayList<String> removeStackTrace(ArrayList<String> arrayListOfLogs) {
        ArrayList<String> newArrayListOfLogs = new ArrayList<String>();
        for (int i = 0; i < arrayListOfLogs.size(); i++) {
            if (!arrayListOfLogs.get(i).startsWith(" ") &&
                    (arrayListOfLogs.get(i).contains(" INFO main ") ||
                            arrayListOfLogs.get(i).contains(" ERROR main ") ||
                            arrayListOfLogs.get(i).contains(" WARN main "))) {
                newArrayListOfLogs.add(arrayListOfLogs.get(i));
            }
        }
        return newArrayListOfLogs;
    }

    /**
     * Replacing all " and ' quotes from log rows
     * @param prerequisitePart - array list with log rows
     * @return array list with log rows without quotes
     */
    private static ArrayList<String> removeQuotes(ArrayList<String> prerequisitePart) {
        ArrayList<String> newPrerequisitePart = new ArrayList<String>();
        for (int i = 0; i < prerequisitePart.size(); i++) {
            newPrerequisitePart.add(prerequisitePart.get(i).replaceAll("\"", "").replaceAll("\'", ""));
        }
        return newPrerequisitePart;
    }

    /**
     * Method create html content right part of report.htm into htmlInfoOfSteps variable
     */
    public static void defineRightPartData() {
        htmlInfoOfSteps += "<table id=\"stepsInfo\">\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Время выполнения:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoTime\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Тип результата шага:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoType\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Поток выполнения:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoThread\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Метод выполнения:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoMethod\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Текст логирования:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoText\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td style=\"width: 200px;\">\n";
        htmlInfoOfSteps += "<a>Скриншот:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "<td>\n";
        htmlInfoOfSteps += "<a id=\"infoScreenShot\"></a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td colspan=\"2\">\n";
        htmlInfoOfSteps += "<a>StackTrace ошибки:</a>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "<tr>\n";
        htmlInfoOfSteps += "<td colspan=\"2\">\n";
        htmlInfoOfSteps += "<textarea id=\"infoStackTrace\" rows=\"1\" readonly></textarea>\n";
        htmlInfoOfSteps += "</td>\n";
        htmlInfoOfSteps += "</tr>\n";
        htmlInfoOfSteps += "</table>\n";
    }
}
