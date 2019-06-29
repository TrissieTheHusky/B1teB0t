[![Version](https://img.shields.io/badge/1.0-Version-brightgreen.svg)](https://github.com/b1tec0de/B1teB0t/releases)
![Java Version](https://img.shields.io/badge/11.0.3-Java-blue.svg)
# B1teB0t
Discord Moderation Bot made with ❤️ by B1teC0de Team

## Discord Hack Week Submission 2019
Category: Moderation

#Setup
You need a MySQL/MariaDB database to run this bot.
Import `b1teb0t.sql` into a database:
 - Via Commandline:
    - `mysql -u username -p database_name < b1teb0t.sql`
 - Via PhpMyAdmin -> Select Import -> Browse file `b1teb0t.sql` -> Go

You can Compile the Bot yourself by using gradle or download it from [Releases](https://github.com/b1tec0de/B1teB0t/releases).

The Bot uses a `config.json`. 
Create the `config.json` file with the layout of [`config.json.example`](https://github.com/b1tec0de/B1teB0t/blob/master/config.json.example) 
and fill it up with your Token and Database Credentials.

After that you can start the Bot with `java -jar B1teB0t-1.0.jar`

Then invite the bot to your Guild- !! Important !! The bot needs Administrator rights.

Now you can use `!help`. There is explained how you setup the bot.

## Features
 - SupportSystem - you get notified when someone of your guild needs help.
 - WarnSystem - Warn(and timeout)/Ban users when they violate rules.
 - ClearSystem - Delete multiple Messages.
 - ActionLog - Know every time what happens on your Guild.

## Licensing
This project is licensed under the [GNU General Public License v3.0](https://choosealicense.com/licenses/gpl-3.0/)

## Dependencies
 - [JDA (Java Discord API)](https://github.com/DV8FromTheWorld/JDA)
 - [Gson](https://github.com/google/gson)
 - [Log4J](https://github.com/apache/log4j)
 - [MySQL Connector Java](https://github.com/mysql/mysql-connector-j)
 
## B1teC0de Team / Contributors
 - [Kaufisch](https://github.com/Kaufisch)
    - Discord: Kaufisch#9536
  - [JavaBasti](https://github.com/JavaBasti0711er)
    - Discord: JavaBasti#2246
