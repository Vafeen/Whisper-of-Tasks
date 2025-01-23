package ru.vafeen.whisperoftasks.tests

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin
import ru.vafeen.whisperoftasks.data.di.MainDataModule
import ru.vafeen.whisperoftasks.domain.database.repository.ReminderLocalRepository
import ru.vafeen.whisperoftasks.domain.di.MainDomainModule
import ru.vafeen.whisperoftasks.domain.domain_models.Reminder
import ru.vafeen.whisperoftasks.domain.duration.RepeatDuration
import ru.vafeen.whisperoftasks.presentation.common.di.MainPresentationModule
import ru.vafeen.whisperoftasks.tests.mock.MainMockModule
import java.time.LocalDateTime

var reminders = mutableListOf<Reminder>(
    Reminder(
        id = 0,
        idOfReminder = 1,
        title = "Завтра встреча",
        text = "Не забудьте о встрече с клиентом.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 1,
        idOfReminder = 2,
        title = "Покупка продуктов",
        text = "Купить молоко, хлеб и яйца.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.EveryDay
    ),
    Reminder(
        id = 2,
        idOfReminder = 3,
        title = "Запись к врачу",
        text = "Записаться на прием к стоматологу.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 3,
        idOfReminder = 4,
        title = "День рождения друга",
        text = "Не забудьте поздравить друга с днем рождения!",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 4,
        idOfReminder = 5,
        title = "Отправить отчет",
        text = "Отправить финансовый отчет до конца недели.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.EveryWeek
    ),
    Reminder(
        id = 5,
        idOfReminder = 6,
        title = "Уборка в квартире",
        text = "Провести генеральную уборку в квартире.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 6,
        idOfReminder = 7,
        title = "Визит к родителям",
        text = "На выходных съездить к родителям.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 7,
        idOfReminder = 8,
        title = "Купить билет на концерт",
        text = "Купить билет на концерт любимой группы.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 8,
        idOfReminder = 9,
        title = "Запланировать отпуск",
        text = "Обсудить с коллегами план отпуска.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.EveryWeek
    ),
    Reminder(
        id = 9,
        idOfReminder = 10,
        title = "Занятия спортом",
        text = "Не забыть о тренировке в зале.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.EveryDay
    ),
    Reminder(
        id = 10,
        idOfReminder = 11,
        title = "Чтение книги",
        text = "Прочитать новую главу книги.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id = 11,
        idOfReminder = 12,
        title = "Проверка почты",
        text = "Проверить электронную почту на наличие важных писем.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.EveryDay
    ),
    Reminder(
        id = 12,
        idOfReminder = 13,
        title = "Оплата счетов",
        text = "Не забыть оплатить счета за коммунальные услуги.",
        dt = LocalDateTime.now(),
        repeatDuration = RepeatDuration.NoRepeat
    ),
    Reminder(
        id=13,
        idOfReminder=14,
        title="Сделать фотографии",
        text="Сделать фотографии на природе.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=14,
        idOfReminder=15,
        title="Встреча с друзьями",
        text="Запланировать встречу с друзьями в выходные.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=15,
        idOfReminder=16,
        title="Посмотреть фильм",
        text="Не забудьте посмотреть новый фильм в кинотеатре.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=16,
        idOfReminder=17,
        title="Проверка здоровья",
        text="Записаться на анализы в клинику.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=17,
        idOfReminder=18,
        title="Подготовка к экзаменам",
        text="Начать подготовку к экзаменам через месяц.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=18,
        idOfReminder=19,
        title="Участие в вебинаре",
        text="Зарегистрироваться на вебинар по программированию.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.NoRepeat
    ),
    Reminder(
        id=19,
        idOfReminder=20,
        title="Планирование бюджета",
        text="Составить бюджет на следующий месяц.",
        dt=LocalDateTime.now(),
        repeatDuration=RepeatDuration.EveryWeek
    )
)

internal class App : Application(), KoinComponent {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(MainPresentationModule, MainDataModule, MainDomainModule, MainMockModule)
        }
        CoroutineScope(Dispatchers.IO).launch {
            getKoin().get<ReminderLocalRepository>().insertAllReminders(reminders)
        }
    }
}