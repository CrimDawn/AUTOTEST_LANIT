package lanit.junit.form.fields.beautifier;

public class IDBeautifier {

    public String beatifyID(String fieldType, String fieldIdentificationValue) {
        if (fieldType.equalsIgnoreCase("Текстовое")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Мемо")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Радио-кнопка")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Чекбокс")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Чекбокс связной")) {
            if (!fieldIdentificationValue.contains("-checkboxGroup")) {
                fieldIdentificationValue = fieldIdentificationValue + "-checkboxGroup";
            }
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Далее")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Назад")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Подать заявление")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Виджет 'Выбрать'")) {
            if (!fieldIdentificationValue.contains("-lookup-form-text")) {
                fieldIdentificationValue = fieldIdentificationValue + "-lookup-form-text";
            }
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Виджет 'Выбрать' с чекбоксом")) {
            if (!fieldIdentificationValue.contains("-lookup-form-text")) {
                fieldIdentificationValue = fieldIdentificationValue + "-lookup-form-text";
            }
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Виджет 'Календарь'")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Загрузка файла")) {
            if (!fieldIdentificationValue.contains(".fileID-upl")) {
                fieldIdentificationValue = fieldIdentificationValue + ".fileID-upl";
            }
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Пропустить шаг вперед")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Пропустить шаг назад")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Заголовок шага")) {
            return fieldIdentificationValue;
        } else if (fieldType.equalsIgnoreCase("Новый сценарий")) {
            return fieldIdentificationValue;
        } else {
            return fieldIdentificationValue;
        }
    }
}
