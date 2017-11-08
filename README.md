# SMRental

## Contributing to our Project

* Get Intellij installed on your platform [here](https://www.jetbrains.com/)
  * You can use your uOttawa email to get a free pro license, highly recommended!
  * Don't forget to grab the JDK and JRE 8 [here](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
     * __osx__: I strongly recommend you update java via homebrew, see below
* Get Git installed and setup
   * This come default with osX and linux but your version might need updating
      * __osx__: Update using the [homebrew project](https://brew.sh/)
      * __linux__: If you are using linux, you probably know this :P
      * __windows__: Download [gitbash](https://git-scm.com/downloads)
   * Setting up your username [githubs tutorial](https://help.github.com/articles/setting-your-username-in-git/)
   * Setting up SSH [github is the real MVP](https://help.github.com/articles/connecting-to-github-with-ssh/)
* Clone this repository
   * Using Git CLI or Git bash:
      * Run ```$git clone git@github.com:NuclearBanane/SMRental.git```
* Import project into Intellij
   * Launch intellij and click "Import Project from Sources"
   * Before clicking continue, make sure the SDK has found JDK8.
      * If JDK8 isn't detected, then navigate to the insall directory
      * __os__: Message me if you have issues
   * Keep clicking continue and ok.
* Test the configuration
   * Right click on Experiment and hit ```main.run()```
* Get the [Zenhub](https://www.zenhub.com/) browser plugin
   * I use this for project management

## Work flow
* Take an issue from the boards in "New Issues" that is unassigned
* Assign it to yourself
* Move it to inprogress
* create a local branch in git
   * ```$ git checkout -b mycool_branch```
* Commit your code to that branch
* push to your branch
* When you are done, move to QA/Review, create a pull request with master
   * tag 2 members of the group as reviewers. Only accept it once they accept your code!
   * If you need to make changes, keep pushing to your branch

## Don't forget
* Try to push 1 commit at a time, you can use ```$ git commit --amend ``` to change your commit if you realized you goofed 
