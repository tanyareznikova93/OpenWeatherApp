# OpenWeatherApp

ТЗ Mobile
Приложение Погода основаное на API OpenWeatherMap (5 Day Weather Forecast)

Screenshots

<img src="screenshots/Screenshot_main.jpg" width="250" height="500"/> <img src="screenshots/Screenshot_favcity.jpg" width="250" height="500"/> <img src="screenshots/Screenshot_changecity.jpg" width="250" height="500"/>

В приложении можно:
- искать погоду по названию города
- получать текущую погоду, почасовой прогноз на сутки, а так же погоду на ближайшие 4 дня (погода отображается с фото)
- можно сохранять/удалять города (удаляется длинным нажатием по выбраному городу)
- из RecyclerView со списком городов можно перейти к погоде по выбраному городу (кликнув на выбраный город, обычным нажатием)
- из RecyclerView со списком городов так же можно вернуться на главную страницу (нажав кнопку со стрелкой)
- после выбора города - отображается актуальная погода, с этого фрагмента можно перейти на фрагмент со списком городов

В проекте применялись:
- Retrofit2
- RxJava2
- Glide
- SQLite
