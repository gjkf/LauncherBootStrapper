Bootstrapper for the Italian Feed the Beast Minecraft Launcher.

For Windows Users

By default it will create a folder in the same directory where the .jar is executed.

In order to access the advanced menu, you need to launch the jar with the extra argument "-e", like this (done in the jar directory):

java -jar bootStrapper.jar -e

For Mac Users

By default it will create a folder in the same directory where the .jar is executed.

In order to access the advanced menu, you need to launch the jar with the extra argument "-e", like this (done in the jar directory):

java -XstartOnFirstThread -jar bootStrapper.jar -e

"-XstartOnFirstThread" is required in order for SWT to work.

Known issues:

On Mac it will just sit there and do nothing when launched the advanced version (see below for more details), not sure why.

Bootstrapper Jar (Stil in Beta): https://www.dropbox.com/s/g384ej3tedk7sz3/bootStrapper.jar?dl=0