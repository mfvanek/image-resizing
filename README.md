# image-resizing
Java image resizing console app

[![Java CI](https://github.com/mfvanek/image-resizing/actions/workflows/tests.yml/badge.svg)](https://github.com/mfvanek/image-resizing/actions/workflows/tests.yml)
[![codecov](https://codecov.io/gh/mfvanek/image-resizing/branch/master/graph/badge.svg?token=A32YQVMNWI)](https://codecov.io/gh/mfvanek/image-resizing)

## How to run
- cd /d D:\src\image-resizing
- java -jar ".\target\image.resizing-1.0-SNAPSHOT-jar-with-dependencies.jar" "https://static.ngs.ru/news/99/preview/e88eba0dbd5cd0e30ee349a3a3c54dbd07d2b28f_712.jpg" 333 444

## Task
### RUS
Написать консольное приложение на Java, которое принимает на вход три параметра:
- URL изображения (формата http://host/path или file:/path)
- width
- height

Результатом выполнения должно быть:
- черно-белое изображение (grayscale)
- приведенное к размерам width x heights
- Изображение можно сохранить в рабочую директорию, выводить путь к изображению.

Приветствуется:
- тесты
- описание того, какие форматы изображений поддерживаются.

## Requirements
Java 17+
