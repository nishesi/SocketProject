# Online Snake Game

## Описание взаимодействия с программой:

1) Игрок запускает клиент, в начальном окне вводит ник
2) Открывается окно со списком комнат. Игрок выбирает одну из них
3) Открывается 3-е окно с с действующей игрой (показывается игра других игроков)
4) Пользователь нажимает на кнопку старт, на карте появляется его змейка, и он начинает управлять ею
5) Игрок проигрывает, игра на карте продолжает транслироваться, пользователь может начать заново либой выйти из комнаты.

## Описание взаимодействия клиента и сервера:

1) Client) После ввода ника пользователем клиент устанавливает соединение с сервером и передает сообщение типа 0,
   Server) Сервер сохраняет состояние соединения и отправляет пользователю список комнат (тип сообщения 1)

2) Client) После выбора пользователем комнаты клиент отправляет серверу индекс выбранной комнаты (тип сообщения 2)
   Server) Сервер начинает с некоторой периодичностью отправлять состояние игры

3) 

4) Client) Пользователь либо начинает игру, либо выходит из комнаты. Клиент отправляет сообщение с соответствующим
   типом.
   Server) 1) Прекращает отправлять состояние игры
           2) Включает пользователя в игру, и начинает ждать сообщений с действиями от пользователя

5) Server) При проигрыше отправляет сообщение о проигрыше (тип сообщения _) и ждет дальнейших действий

## Технические моменты

1) С периодичностью сервер должен спрашивать у пользователя активность (ping-pong типы сообщений 8, 9)
   Если сервер через некоторое время не получил ответа, то он стирает состояние состояние подключения и связанные с игрой данные

2) С периодичностью клиент должен спрашивать у сервера активность(ping-pong)
   Если через некоторое время сервер не отвечает, клиент выводит пользователю сообщение об утере соединения 
   (авто-переподключение не гарантируется)
