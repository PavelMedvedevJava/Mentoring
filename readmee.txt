Задача 

Необходимо реализовать консольное CRUD приложение, которое имеет следующие сущности:

Developer
Skill
Account
AccountStatus (enum ACTIVE, BANNED, DELETED)

Developer -> Set<Skill> skills + Account account
Account -> AccountStatus

В качестве хранилища данных необходимо использовать текстовые файлы:
developers.txt, skills.txt, accounts.txt

Пользователь в консоли должен иметь возможность создания, получения, редактирования и удаления данных.





Запускаете приложение и выбирате действие вводом цифр от 0 до 11 и следуете дальнейшем инструкциям .


