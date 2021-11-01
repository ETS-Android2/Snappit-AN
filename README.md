![GitHub language count](https://img.shields.io/github/languages/count/ClaudiaAF/Snappit-AN?colorB=dd537b)
![GitHub repo size](https://img.shields.io/github/repo-size/ClaudiaAF/Snappit-AN?colorB=dd537b)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/ClaudiaAF/Snappit-AN?colorB=dd537b)
![GitHub watchers](https://img.shields.io/github/watchers/ClaudiaAF/Snappit-AN?colorB=dd537b)



<!-- PROJECT LOGO -->
<br />
<p align="center">
  <a href="https://github.com/claudiaAF/Snappit-AF">
    <img src="https://user-images.githubusercontent.com/64257497/135145069-5d5208e2-8087-4873-8ee4-0f85989b0f44.png" width="195" alt="logo" >
  </a>

  <p align="center">
    Celebrity Recogniser App
    <br />
    <a href="https://github.com/claudiaAF/Snappit-AN"><strong>Explore the docs »</strong></a>
    <br />
    <br />
    <a href="https://github.com/claudiaAF/Snappit-AN">View Demo</a>
    ·
    <a href="https://github.com/claudiaAF/Snappit-AN/issues">Report Bug</a>
    ·
    <a href="https://github.com/claudiaAF/Snappit-AN/issues">Request Feature</a>
  </p>
</p>



<!-- TABLE OF CONTENTS -->
<details open="open">
<summary><h2 style="display: inline-block">Table of Contents</h2></summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#built-with">Built With</a></li>
      </ul>
    </li>
    <li><a href="#getting-started">Getting Started</a>
    <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
      <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#features-and-functionality">Features and Functionality</a>
    </li>
    <li><a href="#concept-process">Concept Process</a>
    <ul>
        <li><a href="#ideation">Ideation</a></li>
      <li><a href="#user-flow">User Flow</a></li>
      <li><a href="#wireframes">Wireframes</a></li>
      </ul>
    </li>
    <li><a href="#development-process">Development Process</a>
    <ul>
      <li><a href="#implementation">Implementation</a>
        <ul>
        <li><a href="#resources-used">Resources Used</a></li>
        <li><a href="#highlights">Highlights</a></li>
        <li><a href="#challenges">Challenges</a></li>
        </ul>
      </li>
      <li><a href="#reviews-from-testing">Reviews from Testing</a></li>
      <li><a href="#future-implementation">Future Implementation</a></li>
      </ul>
    </li>
    <li>
      <a href="#final-outcome">Final Outcome</a>
      <ul>
        <li><a href="#mockups">Mockups</a></li>
        <li><a href="#video-demonstration">Video Demonstration</a></li>
      </ul>
    </li>    
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgements">Acknowledgements</a></li>
  </ol>
</details>


<img width="1921" alt="Slide 16_9 - 26" src="https://user-images.githubusercontent.com/64257497/139661982-93ec89a5-92c5-4519-a406-b5dda0aabea9.png">

<!-- ABOUT THE PROJECT -->
## About The Project
Snappit. is an Android application aimed at providing media insight to the user at the tap of a button. If you find yourself constantly trying to remember the name of an actor you saw in a movie you are watching, or just simply saw anywhere, use Snappit. to find that familiar face in seconds. This method follows one of 6 psychological tactics used by the most popular apps to grab attention, The Principle of The Least Effort. This involves no thinking from the user, the actions are automatic, impulsive and driven by emotion (Obtained from https://clevertap.com/blog/psychology-of-successful-mobile-apps-infographic/).

Along with the actors details, a list of their most popular movies will also be presented as a “You might have seen this actor in these” section. This way the user will be able to gauge in where else they might have seen the actor, and possibly discover new movies. This secondhandedly exposes the user to more films in the industry and expand their cultural knowledge.  

### Built With
<p float="left">
<a href="htthttps://developer.android.com/studio?gclid=CjwKCAjwoP6LBhBlEiwAvCcthPsbscldTQYgMujyCB729OUvni0PRdqJ7v7b_HttvvLu4jMxrX7L_xoCQtYQAvD_BwE&gclsrc=aw.ds">
    <img src="https://user-images.githubusercontent.com/64257497/139662394-dc89ac6c-eced-4a66-a7ae-be53a3b012ad.png" width="100" alt="logo" >
 </a>
&nbsp;&nbsp;&nbsp;
 <a href="https://www.java.com/en/">
    <img src="https://user-images.githubusercontent.com/64257497/139662646-701cfda6-8341-4c84-a7d4-715959b688e7.png" width="50" alt="logo" >
 </a>
  &nbsp;&nbsp;&nbsp;
 <a href="https://imdb-api.com/api">
    <img src="https://user-images.githubusercontent.com/64257497/139663300-e931fb77-1ccf-4641-b59e-a6f60f6b3568.png" width="100" alt="logo" >
 </a>
   &nbsp;&nbsp;&nbsp;
 <a href="https://aws.amazon.com/rekognition/">
    <img src="https://user-images.githubusercontent.com/64257497/139663475-bcc080e1-9631-4881-ab21-46b80ed6ac5b.png" width="190" alt="logo" >
 </a>
  </p>
  
## Getting Started

### Prerequisites
1. Android Studio version 4.1.2 and later. 
2. A free AWS Rekognition account, so that you can access your own personal `Access key ID` and `Secret Key` .

### Installation
1. Open Android Studio
   Then navigate to `File` > `New` > `From Version Control` > `Git`     
2. Copy and paste the repo link `https://github.com/ClaudiaAF/Snappit-AN.git` into the URL field and continue to click the `Clone` option.
3. Insert your AWS `Access key ID` and `Secret Key` into the `AWSUtil.java` activity.  
4. If the Liquid Swipe does not work on first run, please refer to the documentation on Cuberto's Github <a href="https://github.com/Cuberto/liquid-swipe-android.git">here</a> to set it up correctly.

<!-- USAGE EXAMPLES -->
## Features and Functionality
<img width="1920" alt="Slide 16_9 - 17" src="https://user-images.githubusercontent.com/64257497/139668161-d04a2cce-a01a-489a-952f-7b589a663f85.png">
<img width="1920" alt="Slide 16_9 - 22" src="https://user-images.githubusercontent.com/64257497/139668979-a53e742b-1683-40d6-9245-aea179552f7c.png">
<img width="1920" alt="Slide 16_9 - 25" src="https://user-images.githubusercontent.com/64257497/139668371-e1641d94-dcbf-4bab-9c9f-13087d76049f.png">
<img width="1920" alt="Slide 16_9 - 23" src="https://user-images.githubusercontent.com/64257497/139669121-352838c8-e3fb-4f3c-b4dc-09331687fa7c.png">
<img width="1920" alt="Slide 16_9 - 24" src="https://user-images.githubusercontent.com/64257497/139668423-cddf3dcb-9a52-4478-a0ae-1fc88606a757.png">

## Concept Process
### Ideation
<img width="1708" alt="Slide 16_9 - 13" src="https://user-images.githubusercontent.com/64257497/139671427-258b4234-253b-4eb7-b12d-f624999b9b5c.png">

### User Flow
<p align="center">
<img width="1351" alt="Group 52" src="https://user-images.githubusercontent.com/64257497/139671074-a3d18ac1-8391-47ee-ac6d-c61d459b2f39.png">
</p>

### Wireframes
<img width="1787" alt="Slide 16_9 - 20" src="https://user-images.githubusercontent.com/64257497/139671443-d1b7243a-e635-46fb-b02f-8ad3e28fc26c.png">

## Development Process
The technical implementations and functionality done in the frontend and backend of the application.

## Implementation
### Resources Used
* <a href="https://github.com/Cuberto/liquid-swipe-android.git">Cuberto Library</a> for the liquid swipe.
* `OkHTTP` dependency to handle API calls to IMDb API.
* `Picasso` dependency to manage loading images from JSON objects into front-end.
* `WebView` to embed Youtube Video.

### Highlights
* Being able to implement the <a href="https://github.com/Cuberto/liquid-swipe-android.git">Cuberto Liquid Swipe</a>
* Utilising the beautiful 3D Illustrations by <a href="https://dribbble.com/alzea">Alzea Arafat</a>
* Getting the AWS Rekognition AI to work, and the IMDb to work alongside that too.
* Managing to make a second call to IMDb for the movie trailers of each movie in the "Known For" section.

### Challenges
* Getting the camera and photo upload from taking a picture in the camera to work.
* Setting unique ID's to each movie poster.
* Layout for displaying the movie posters posed to be trickier than expected.

## Reviews From Testing
### Feedback from Reviews
`Peer Reviews` were conducted by my fellow students and lecturer. The following feedback I found useful:

* "The is simple and easy to use, which is exactly what is needed for this application"
* "It is super minimal and simple, easy to understand, having one big icon on the home page, like you said updating the icon to the specific task that is going to do, is going to help a lot."
* "More information on the actor and the movies would be really cool after uploading."
* "Maybe link to the movie trailers or filter movies based on rating"
* "Maybe just adjust the iconography to be easier to understand, but otherwise I like the overall concept of the application. You could also maybe include a little bit more information about the celebrities (fun facts) :)" 

### Future Implementation
* Include a function which remembers all of the users activity, and then integrate the movie playlist option which the app would personally curate for the user based on their activity
* Another nice addition would be a widget for quick access on the phones home page.

## Final Outcome
### Mockups
![Group 54](https://user-images.githubusercontent.com/64257497/139680467-5185a5e7-8aa0-427b-a4b1-9a9103d21d1e.png)
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
![Group 55](https://user-images.githubusercontent.com/64257497/139680478-b773aaa3-9052-488b-ac41-c42a3b468bd5.png)

### Video Demonstration


https://user-images.githubusercontent.com/64257497/139685196-903eb1a3-0d8c-43a5-be39-2e007200b157.mp4



Click <a href="https://drive.google.com/file/d/1QXrJQOuAZ_5t4P9lWcLAZCXnLEOQOUIa/view?usp=sharing">here</a> to view the video demonstration from Google Drive.

<!-- ROADMAP -->
## Roadmap

See the [open issues](https://github.com/github_username/repo_name/issues) for a list of proposed features (and known issues).


<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to be learn, inspire, and create. Any contributions you make are **greatly appreciated**.

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/Snappit-AN`)
3. Commit your Changes (`git commit -m 'Add some Snappit-AN'`)
4. Push to the Branch (`git push origin feature/Snappit-AN`)
5. Open a Pull Request



<!-- LICENSE -->
## License

Distributed under the MIT License. See `LICENSE` for more information.



<!-- CONTACT -->
## Contact

* **Claudia Ferreira** - 180181@virtualwindow.co.za
* **Project Link** - https://github.com/ClaudiaAF/Snappit-AN.git



<!-- ACKNOWLEDGEMENTS -->
## Acknowledgements
* <a href="https://github.com/ArmandPretorius">Armand Pretorius</a>
* Loader Explanation - <a href="https://stackoverflow.com/questions/12559461/how-to-show-progress-barcircle-in-an-activity-having-a-listview-before-loading">Stack Overflow</a>
* Embed a youtube video - <a href="https://stackoverflow.com/questions/17072421/embed-youtube-video-inside-an-android-app">Stack Overflow</a>
* Adding onclick events to dynamically generated images in android relative layout - <a href="https://stackoverflow.com/questions/11044972/adding-onclick-events-to-dynamically-generated-images-in-android-relative-layout">Stack Overflow</a>
* <a href="https://github.com/Cuberto/liquid-swipe-android.git">Cuberto Liquid Swipe</a>
* [Plagiarism Form](PlagiarismForm_ClaudiaFerreira.png)


<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/github_username/repo.svg?style=for-the-badge
[contributors-url]: https://github.com/github_username/repo/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/github_username/repo.svg?style=for-the-badge
[forks-url]: https://github.com/github_username/repo/network/members
[stars-shield]: https://img.shields.io/github/stars/github_username/repo.svg?style=for-the-badge
[stars-url]: https://github.com/github_username/repo/stargazers
[issues-shield]: https://img.shields.io/github/issues/github_username/repo.svg?style=for-the-badge
[issues-url]: https://github.com/github_username/repo/issues
[license-shield]: https://img.shields.io/github/license/github_username/repo.svg?style=for-the-badge
[license-url]: https://github.com/github_username/repo/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=d0ac4b
[linkedin-url]: https://linkedin.com/in/ClaudiaAF
