This project has one goal:

To help newbies build plugins, and to help save time for people who need a basic core up and can't be bothered with maven setup.
The code here might be terrible, so i posted it for the reasons above and to also get someone possibly to suggest better cleaner types of code that keep the simplicity of this project. 
This isn't merely anything more than a time saver for people or to get newbies to have a base to start learning on how to make plugins.

Thank you for your contributions.

Current Spigot API: 1.21.5

Current Functions that might be odd for some people:
there's a function called translate which allows you to do something like this: translate("&cHello&7, &aworld&8!");
this will translate the color codes into something similar as if you were to do ChatColor.RED + "Hello" + ChatColor.GRAY + "," + ChatColor.GREEN + "world" + ChatColor.DARKGRAY +"!";
compared to that it's much simpler to write, but may or may not be as dynamic. Depends on your work flow
