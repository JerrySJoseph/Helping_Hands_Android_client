# Helping Hands Client for android :zap:

<!--- These are examples. See https://shields.io for others or to customize this set of shields. You might want to include dependencies, project status and licence info here --->
![Github versions](https://img.shields.io/github/v/release/jerrysjoseph/Helping_Hands_Android_client?include_prereleases)
![GitHub repo size](https://img.shields.io/github/repo-size/jerrysjoseph/Helping_Hands_Android_client)
![GitHub contributors](https://img.shields.io/github/contributors/jerrysjoseph/Helping_Hands_Android_client)
![GitHub stars](https://img.shields.io/github/stars/jerrysjoseph/Helping_Hands_Android_client?style=social)
![GitHub forks](https://img.shields.io/github/forks/jerrysjoseph/Helping_Hands_Android_client?style=social)

Helping Hands is an Open Source Android application built by Jerry S Joseph. This application serves as a fully functional prototype of the social service community app. 
This app was built in JAVA using MVVM Architecture pattern.

## Modules
* app -> The actual application
* HelpinghandlsCloud -> Module for handling Network/cloud requests

## Demo

### Parallel Processing

Tasks are processed simultaneously reducing the runtime.

<p align="center">
  <img width="auto" height="auto" alt="parallel processing" src="gifs/backgroundTaskManager2.gif">
</p>


### Serial Processing

Tasks are processed one after other.

<p align="center">
  <img width="auto" height="auto" alt="serial processing" src="gifs/backgroundTaskManager3.gif">
</p>


## Goals :fire:
- [x] No Login/Signup.
- [x] Identify device using unique ID.
- [x] Consume Rest API Endpoints in a easier and cleaner way.
- [x] Ability to work without network connections (Once the data is fetched from server, it is stored in LocalDB unless new data is available).
- [x] Generate matches tailored for every user by location and rating.
- [x] Develop a scoring system for backend which generates match nearest and most trusted to you.
- [x] No user data is stored in the server. User is always anonymous. 
- [x] Add service contacts.
- [ ] Service contact verification (By phone ).
- [ ] Filter services by location, date, rating, etc.


## Contributing to this project :boy:
<!--- If your README is long or you have some specific process or steps you want contributors to follow, consider creating a separate CONTRIBUTING.md file--->
To contribute to this project, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively see the GitHub documentation on [creating a pull request](https://help.github.com/en/github/collaborating-with-issues-and-pull-requests/creating-a-pull-request).

## Contributors :boy:

Thanks to the following people who have contributed to this project:

* [@jerrysjoseph](https://github.com/JerrySJoseph) :memo: :computer:


## Contact :telephone:

If you want to contact me you can reach me at <jerin.sebastian153@gmail.com>.

## License
<!--- If you're not sure which open license to use see https://choosealicense.com/--->

This project uses the following license: [Apache-2.0](LICENSE.txt).
