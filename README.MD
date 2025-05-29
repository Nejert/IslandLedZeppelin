# JRU Animal Island Led Zeppelin
### Completed tasks:
- Animal hierarchy (OOP) (at least 10 herbivores and 5 carnivores species created).
- Animal behavior (`eat()`, `reproduce()`, `move()`).
- Multithreading.
- State statistics of the island at each tick (in the console).
- Bring parameters to one place for convenience of manage the simulation.
- Swing graphics.
### Animal capabilities:
- Eat plants or other animals if suitable food in the location occurred. Decision based on animal eating probability table (see `EatingProbability`).
- Move to neighboring cells. Have an ability to move to the initial location, returning to the initial location is not prohibited. (see `MoveSystem`)
- Reproduce if a pair in their location occurred. Probability of having offspring is determined as a percentage (see `ReproduceSystem`, `Config#birthProbability`)
- Die either from starvation or be eaten (see `EatSystem`, `EatSystem#killStarvingAnimals`).
### Configuration:
Application is controlled by three configuration files.

    config.xml
    entityConfig.json
    initialSpawnProbabilityConfig.yaml

This is done for educational purposes, to touch each of the serialization technologies described in the lecture. All properties are optional.
___
#### `config.xml` - common configuration of application.
**Properties:**
>`rows` - number of cell rows.\
`cols` - number of cell columns.\
`animalMovesCount` - number of ticks during which an animal can move without being fed.\
`birthProbability` - percentage of successful birth.\
`tickPeriodMs` - period in milliseconds during which playing field is updated.\
`width` - width of the swing view window.\
`height` - height of the swing view window.\
`gridStrokeWidth` - width of the line separating each cell.\
`statWidthPercentage` - width of the statistics pane in percent.\
`entityGroupCapacity` - capacity of the group of entities located in each cell. Once the maximum is reached, a new group is added to which new entities are placed for display.\
`backgroundColor` - background color in hexadecimal format e.g `#XXXXXX`.\
`gridLinesColor` - color of the lines separating each cell in hexadecimal format e.g `#XXXXXX`.\
`windowTitle` -  title of the application window.\
`spawnConfig` - relative path to initial spawn probability config.\
`entityConfig` - relative path to entities config.
___
#### `entityConfig.json` - allows to configure each entity (animals, plants).
**Properties:**
>`weight` - entity weight.\
`maxQuantity` - maximum number entities of this type in one cell.\
`maxSteps` - animal movement speed. No more than steps per move.\
`saturation` - number of food kilograms does an animal need to be fully saturated.\
`icon` - textual representation of the entity emoji.\
`imageIcon` - relative path to the entity image.
___
#### `initialSpawnProbabilityConfig.yaml` - configuration responsible for the initial allocation of entities on the map in percent.
___
### Build:
```
mvn clean compile assembly:single
```

### Run:
Windows Terminal (cmd):
```
chcp 65001
cd target/
java -jar Island.jar
```
PowerShell:
```
[Console]::InputEncoding = [Console]::OutputEncoding = New-Object System.Text.UTF8Encoding
cd target/
java -jar Island.jar
```
Other:
```
cd target/
java -jar Island.jar
```
___
<h1>Остров животных</h1>

_(пример для группы LedZeppelin)_

__Будет полезен на консультациях, ну и для подготовки к модулю 3.__

Ребята и девчата, всем привет! Вот наш второй проект.
Все детали есть в задании Javarush, поэтому тут я их повторять не буду.

Надеюсь вам понравится, несмотря на сложность у меня по-прежнему нет планов ограничивать вашу фантазию.
Как мы знаем свобода в принятии решений - это самое интересное и приятное в работе разработчика.
Если будут какие-то ошибки, не волнуйтесь - это будет ОЧЕНЬ полезно разобрать на ревью.

Тут я буду постепенно показывать примерную версию нашего проекта.
Код уже надеюсь вам будет не так сложен (много из Core мы разобрали уже), 
я постараюсь покрыть все уровни подготовки в группе. 

Конечно всегда можно "подсмотреть" какие-то решения, 
но ОЧЕНЬ советую по возможности не копировать.

Итак, далее - как обычно. Вам нужно:
1. Клонировать этот репозиторий <a href="https://github.com/demologin/IslandLedZeppelin/fork">[FORK]</a>
2. Скачать из своего репозитория полученную копию
3. Добавить пакет на основе своей фамилии ***lastname*** маленькими(!) буквами по пути */src/main/java/com/javarush/island/**lastname***.
4. Придумать и разместить в нем свое решение (а вот тут уже полная свобода действий)
5. Сдать проект (нужно будет заполнить форму и прислать мне Pull Request на проверку)

Пример будет в пакете khmelov, там я буду размещать все что будем разбирать на консультациях.
На выполнение предусмотрено 2 недели.

Через две недели после завершения второго модуля мы выходим на ревью первого сданного проекта. 
Но если кто-то сдаст раньше, технически можем начать ревью с опережением этого графика.
Проверять в основном буду код, но как все выглядит тоже важно. Мы это тоже все посмотрим.
Кода будет много, проверять буду долго, поэтому если опоздаете к deadline - 
это не так страшно (как на работе) и вполне допускается. Учеба же.

**Как можно оформить вывод:**

1. Консольный (предполагается как основной - с ним все хорошо знакомы. Так можно
   <a href="https://www.google.com/search?q=ascii+color+java&oq=ascii+color+java">добавить цвет</a>)
2. Swing (я покажу еще JavaFX - там можно те же принципы использовать)
3. Что-то свое (диаграммы, псевдографика, что-то другое, короче полная свобода действий)

Я снова сделал проект на основе maven, но зачем? Мы же его не проходили!

Если вдруг кому-то захочется поменять зависимости и "пилить" на Spring или JavaFX - никаких проблем.
Но только тогда, когда будете сдавать предупредите меня, чтобы я завел для вас отдельную ветку в репозитории.
Кто ничего из этого не понял, просто делаем свое решение в своем пакете. Как потом его сдавать я расскажу и покажу.

Всех с началом второго проекта и успешным (я надеюсь) завершением большого и реально сложного блока Java Core!
Дальше будет гораздо интереснее, backend - это занимательный мир для любого разработчика. 

_Ментор JRU Александр Хмелев._
