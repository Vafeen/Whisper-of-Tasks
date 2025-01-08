package ru.vafeen.whisperoftasks.data.mock.database

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.first
import ru.vafeen.whisperoftasks.domain.database.ReminderRepository
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import java.time.LocalDate
import java.time.LocalDateTime

internal class MockReminderRepositoryImpl : ReminderRepository {
    private var reminders = mutableListOf<Reminder>(
        Reminder(
            id = 0,
            idOfReminder = 1,
            title = "Завтра встреча",
            text = "Не забудьте о встрече с клиентом.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(1)
        ),
        Reminder(
            id = 1,
            idOfReminder = 2,
            title = "Покупка продуктов",
            text = "Купить молоко, хлеб и яйца.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryDay,
            dateOfDone = LocalDate.now().plusDays(2)
        ),
        Reminder(
            id = 2,
            idOfReminder = 3,
            title = "Запись к врачу",
            text = "Записаться на прием к стоматологу.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(3)
        ),
        Reminder(
            id = 3,
            idOfReminder = 4,
            title = "День рождения друга",
            text = "Не забудьте поздравить друга с днем рождения!",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(4)
        ),
        Reminder(
            id = 4,
            idOfReminder = 5,
            title = "Отправить отчет",
            text = "Отправить финансовый отчет до конца недели.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryWeek,
            dateOfDone = LocalDate.now().plusDays(5)
        ),
        Reminder(
            id = 5,
            idOfReminder = 6,
            title = "Уборка в квартире",
            text = "Провести генеральную уборку в квартире.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(6)
        ),
        Reminder(
            id = 6,
            idOfReminder = 7,
            title = "Визит к родителям",
            text = "На выходных съездить к родителям.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(7)
        ),
        Reminder(
            id = 7,
            idOfReminder = 8,
            title = "Купить билет на концерт",
            text = "Купить билет на концерт любимой группы.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(8)
        ),
        Reminder(
            id = 8,
            idOfReminder = 9,
            title = "Запланировать отпуск",
            text = "Обсудить с коллегами план отпуска.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryWeek,
            dateOfDone = LocalDate.now().plusDays(9)
        ),
        Reminder(
            id = 9,
            idOfReminder = 10,
            title = "Занятия спортом",
            text = "Не забыть о тренировке в зале.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryDay,
            dateOfDone = LocalDate.now().plusDays(10)
        ),
        Reminder(
            id = 10,
            idOfReminder = 11,
            title = "Чтение книги",
            text = "Прочитать новую главу книги.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(11)
        ),
        Reminder(
            id = 11,
            idOfReminder = 12,
            title = "Проверка почты",
            text = "Проверить электронную почту на наличие важных писем.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryDay,
            dateOfDone = LocalDate.now().plusDays(12)
        ),
        Reminder(
            id = 12,
            idOfReminder = 13,
            title = "Оплата счетов",
            text = "Не забыть оплатить счета за коммунальные услуги.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(13)
        ),
        Reminder(
            id = 13,
            idOfReminder = 14,
            title = "Сделать фотографии",
            text = "Сделать фотографии на природе.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(14)
        ),
        Reminder(
            id = 14,
            idOfReminder = 15,
            title = "Встреча с друзьями",
            text = "Запланировать встречу с друзьями в выходные.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(15)
        ),
        Reminder(
            id = 15,
            idOfReminder = 16,
            title = "Посмотреть фильм",
            text = "Не забудьте посмотреть новый фильм в кинотеатре.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(16)
        ),
        Reminder(
            id = 16,
            idOfReminder = 17,
            title = "Проверка здоровья",
            text = "Записаться на анализы в клинику.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(17)
        ),
        Reminder(
            id = 17,
            idOfReminder = 18,
            title = "Подготовка к экзаменам",
            text = "Начать подготовку к экзаменам через месяц.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(18)
        ),
        Reminder(
            id = 18,
            idOfReminder = 19,
            title = "Участие в вебинаре",
            text = "Зарегистрироваться на вебинар по программированию.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.NoRepeat,
            dateOfDone = LocalDate.now().plusDays(19)
        ),
        Reminder(
            id = 19,
            idOfReminder = 20,
            title = "Планирование бюджета",
            text = "Составить бюджет на следующий месяц.",
            dt = LocalDateTime.now(),
            repeatDuration = RepeatDuration.EveryWeek,
            dateOfDone = LocalDate.now().plusDays(20)
        )
    )

    private val remindersFlow = MutableStateFlow(reminders.toList())
    override fun getAllRemindersAsFlow(): Flow<List<Reminder>> = remindersFlow

    override suspend fun getReminderByIdOfReminder(idOfReminder: Int): Reminder? =
        remindersFlow.first().firstOrNull {
            it.idOfReminder == idOfReminder
        }

    override suspend fun insertAllReminders(vararg entity: Reminder) {
        val ids = entity.map { it.idOfReminder }
        reminders.removeAll {
            it.idOfReminder in ids
        }
        reminders.addAll(entity)
        updateFlow()
    }

    override suspend fun deleteAllReminders(vararg entity: Reminder) {
        entity.forEach {
            reminders.remove(it)
        }
        updateFlow()
    }


    private suspend fun updateFlow() {
        remindersFlow.emit(reminders.toList())
    }
}