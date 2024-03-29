<hr/>

# ⚠️ Deprecation warning

This project has been deprecated in favor of our latest [kmp-boilerplate](https://github.com/mirego/kmp-boilerplate).

<hr/>

# Trikot.patron 
This repository is the stable base upon which we build our mobile projects at Mirego.
We want to share it with the world so you can build awesome mobile applications too.

## Introduction

To learn more about _why_ we created and maintain this boilerplate project, read our [blog post](https://shift.mirego.com/en/boilerplate-projects).

## Usage

### Setting up the project

#### With GitHub template

1. Click on the [**Use this template**](https://github.com/mirego/trikot.patron/generate) button to create a new repository
2. Clone your newly created project (`git clone https://github.com/you/repo.git`)
3. Run the boilerplate setup script (`./boilerplate-setup.sh YourProjectName`)
4. Commit the changes (`git commit -a -m "Rename Trikot.patron parts"`)

#### Without GitHub template

1. Clone this project (`git clone https://github.com/mirego/trikot.patron.git`)
2. Delete the internal Git directory (`rm -rf .git`)
3. Run the boilerplate setup script (`./boilerplate-setup.sh YourProjectName`)
4. Create a new Git repository (`git init`)
5. Create the initial Git commit (`git commit -a -m "Initial commit"`)

### Building the project

1. Open root folder in Android Studio to run the Android App.
2. In a terminal, cd into `ios` folder and run `bundle exec pod install` to be able to run the iOS App.

## What to Replace

## Root

- `./jobs/build_jobs.groovy`
- `./jobs/ios_fastlane.groovy`

```groovy
String clientName = 'SAMPLECLIENT'.toLowerCase().replaceAll(' ','_')
String projectName = 'SAMPLEPROJECT'.toLowerCase().replaceAll(' ','_')
String projectGithubPath = 'mirego/SAMPLEREPO'
String slackNotificationChannel = '#SAMPLESLACKCHANNEL'
```

### TODO:

- [ ] Keystores
- [ ] Fastfiles

## License

Trikot.streams is © 2019-2020 [Mirego](https://www.mirego.com) and may be freely distributed under the [New BSD license](http://opensource.org/licenses/BSD-3-Clause). See the [`LICENSE.md`](https://github.com/mirego/trikot.patron/blob/master/LICENSE.md) file.

## About Mirego

[Mirego](https://www.mirego.com) is a team of passionate people who believe that work is a place where you can innovate and have fun. We’re a team of [talented people](https://life.mirego.com) who imagine and build beautiful Web and mobile applications. We come together to share ideas and [change the world](http://www.mirego.org).

We also [love open-source software](https://open.mirego.com) and we try to give back to the community as much as we can.
