# Spring Boot Playground Project

### Application description
It is necessary to create an application that allows users to register and after successful registration to create an album and add/upload images to the album. While adding images, the user can add hashtags or tag other users.

All other registered users can see the homepage where they should be able to search for images using hashtags or user tags with suggestions. Also, users can give their rating to the image (thumbs up / down or like and dislike).

### Required functionality

- Homepage
  - list images
  - search images using hashtags or user tags with suggestions
  - if the user is logged in, display the rating buttons.
  - preselect button if user like or dislike specific image
- Profile
  - display all private albums
  - create a new private album
- Album Details
  - list images for the specified album
  - add a new image with suggestions for:
    - hashtags (#nature, #sun, #love, #cat)
    - user (foo@bar, john@doe)
- Login page
- Register page
  - send an email to the activate account
