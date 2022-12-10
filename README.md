# Recipe Manager

<img src="images/chef.png" height="100" alt="A cat wearing a chef hat"/>

**Recipe Manager** is an application for managing recipes in a user-friendly interface on your local computer. You can store your recipes, tag them, search them, and even get recommendations on what recipe to cook next. You can export and import recipes to share between your friends, or keep all your recipes secret!

### Running the Recipe Manager

From within IntelliJ, select one of the following two run configurations from the run menu near the top right:

1. **In Memory Recipe Manager**: this run configuration lets you run the recipe manager in a testing environment. None of the recipes you create in this run configuration will be saved once it stops running. 
2. **Local File Recipe Manager**: this run configuration is the recommended option. All recipes you create in this run configuration will be saved to your computer for the next time you run the recipe manager.

### Troubleshooting

> **Note**
> Having trouble using the run configurations?

<img src="images/config-error.png" alt="An error with the run configuration"/>

If you're getting an error like the highlighted run configuration above, follow these steps:

1. Click "Edit Configurations..." from the run menu (in the image above)
2. Change the selected module to "main"
<img src="images/changing-module.png" alt="Changing run config module"/>
3. Click "Apply" to save your changes. You should now be able to run the configuration from the run menu.
