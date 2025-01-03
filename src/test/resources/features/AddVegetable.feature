
# language: ru
Функция: Добавление овощей

  Сценарий: Добавление неэкзотического овоща
    Дано открыть приложение
    Когда переход на страницу Товары
    И нажать на кнопку Добавить
    И ввод "морковь" как название продукта
    И выбирать "VEGETABLE" как тип продукта
    И нажать кнопку "save"
    Тогда должны увидеть "морковь" в списке товаров

  Сценарий: Добавление экзотического овоща
    Дано открыть приложение
    Когда переход на страницу Товары
    И нажать на кнопку Добавить
    И ввод "Момордика" как название продукта
    И выбирать "VEGETABLE" как тип продукта
    И отметить чекбокс Экзотический
    И нажать кнопку "save"
    Тогда должны увидеть "Момордика" в списке товаров
    Когда сброс данных
