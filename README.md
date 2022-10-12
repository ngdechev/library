![version](https://img.shields.io/badge/version-1.0.0-blue)
![GitHub repo size](https://img.shields.io/github/repo-size/ngdechev/library?color=yellow)
![Bitbucket open issues](https://img.shields.io/bitbucket/issues/ngdechev/library)
![java-version](https://img.shields.io/badge/java--version-18-orange)


# Проект по Обектно-Ориентирано Програмиране
## Задание - "Библиотека"

Да се напише програма, реализираща информационна система, която поддържа библиотека. Програмата съхранява и обработва данни за наличните в момента книги във файл.

Всяка книга се характеризира със  следните данни: 
- aвтор
- заглавие
- жанр 
- кратко описание 
- година на издаване 
- ключови думи 
- рейтинг
- уникален номер за библиотеката 

Системата поддържа два вида потребители — администратори и клиенти на библиотеката. Всеки потребител се характеризира със следните данни:

- потребителско име
- парола 
- ниво на достъп — указва дали потребителят е администратор или не

След като приложението отвори даден файл, то трябва да може да извършва посочените по-долу операции, в допълнение на общите операции (open, close, save, save as, help и exit): 

|Команда              	             |  Описание                                                                                                                                                                                                                                                              |
|-------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| login                               | След въвеждането на командата потребителят последователно е питан за потребителско име и парола. Ако потребител с посочените данни съществува в програмата, се извежда съобщение “Welcome, \<username>!”, където \<username> съответства на  потребителското име. В противен случай се извежда съобщение за грешно име или парола. При повторен опит за login, се изкарва съобщение “You are already logged in.”                                                           |
| logout                              | Потребителят напуска системата (програмата продължава да работи)                                                                                                                                                                                               |
| books all                           | Извежда последователно за всяка книга следната информация: - заглавие, автор, жанр,  персонален номер                                                                                                                                                                             |
| books info \<isbn_value>              | Извежда на екрана подробна информация за книга с персонален номер равен на \<isbn_value> <br> Пример: books info 1124                                                                                                                                                                                      |
| books find \<option> <option_string> | \<option> е едно от title, author или tag <br> \<option_string> е стойността на критерия за търсене, може да съдържа интервали <br> Примери: <br> books find title Introduction to programming <br> books find author Stephen King <br> books find tag superhero |
| books sort \<option> [asc \| desc]   | \<option> е едно от title, author, year, rating <br> asc означава възходящо сортиране (по подразбиране), а desc <br> означава низходящо сортиране <br> <u>Примери:</u> <br> books sort title <br> books sort year desc                                          |
| users add \<user> <password>         | Добавя нов потребител с потребителско име \<user> и парола \<password>. Потребителят и неговата парола се записват във файл.                                                                                                                                     |
| users remove \<user>                 | Изтрива потребителя с потребителско име \<user> от файла.                                                                                                                                                                                                       |                                                                                                                |

При първоначално стартиране на програмата няма налични данни за книги. Има регистриран по подразбиране само един потребител с администраторски акаунт със следните данни: 
- потребителско име: admin
- парола:   i<3c++

Програмата очаква да се въведе команда, като след въвеждането и се изпълнява според дефинираните правила. Това продължава до въвеждането на командата “exit”, която прекратява програмата.  

Долната таблица описва за всяка от командите дали е достъпна само при коректно влязъл потребител и дали е ограничена само за потребителя admin. 


| Команда      | Изисква потребител? | Само за администратор? |
|--------------|---------------------|------------------------|
| open         | не                  | не                     |
| close        | не                  | не                     |
| save         | не                  | не                     |
| saveas       | не                  | не                     |
| help         | не                  | не                     |
| login        | не                  | не                     |
| logout       | да                  | не                     |
| exit         | не                  | не                     |
| books all    | да                  | не                     |
| books find   | да                  | не                     |
| books sort   | да                  | не                     |
| books view   | да                  | не                     |
| books add    | да                  | да                     |
| books remove | да                  | да                     |
| users add    | да                  | да                     |
| users remove | да                  | да                     |

- при въвеждане на паролата на екрана да се изписва  символа * вместо реалния символ 
- при сортиране на книгите по зададен критерий, да се напише алгоритъм различен от пряка селекция и метода на мехурчето
- Търсене на книга по зададен критерий да игнорира регистъра на буквите (малки или големи)

## Пускане на програмата (.jar файла)
```bash
java -cp“library.jar;lib/* library.App
```

## Разработка на проекта
За изработката на проекта са използвани следните езици за програмиране:
1. Java
2. XML

И следните библиотеки:

1. JAXB

И следните инструменти:

1. IntelliJ IDEA

![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white) <a href='https://github.com/shivamkapasia0' target="_blank"><img alt='XML' src='https://img.shields.io/badge/XML-100000?style=for-the-badge&logo=XML&logoColor=FF7700&labelColor=FF7700&color=FF7700'/></a> <a href='https://github.com/shivamkapasia0' target="_blank"><img alt='JAXB' src='https://img.shields.io/badge/JAXB-100000?style=for-the-badge&logo=JAXB&logoColor=FF7700&labelColor=4FAE50&color=0A647C'/></a> ![IntelliJ IDEA](https://img.shields.io/badge/IntelliJIDEA-000000.svg?style=for-the-badge&logo=intellij-idea&logoColor=white)

## Източници 
1. https://www.geeksforgeeks.org/cocktail-sort/
2. https://stackoverflow.com/a/45303637
3. https://stackoverflow.com/a/1051705

## Скрийншоти от изпълнението на програмата
### Начална страница
![1.png](https://i.postimg.cc/CxCHTzJN/1.png)
### Използване на програмата с примерни данни
![2.png](https://i.postimg.cc/0N8G8PBq/2.png)

![3.png](https://i.postimg.cc/d0423pbZ/3.png)

![4.png](https://i.postimg.cc/FRzjLGvW/4.png)

![5.png](https://i.postimg.cc/RFYwQfVM/5.png)
