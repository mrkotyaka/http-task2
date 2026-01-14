# Java Core
# Homework assignment for class: HTTP protocol. Remote server calls

The following tasks must be completed and submitted for review:

1. [Request to obtain a list of facts about cats](https://github.com/mrkotyaka/http-task1#java-core);
2. [Reading NASA API data](#point_2) (task marked with an asterisk *).

=======

# <a name="point_2">Reading NASA API data</a>

## Description
You need to use the public NASA API and download the images or other content (e.g., videos) that it uploads daily.
Although the API is public, access to it is provided by a key, which is easy to obtain at: https://api.nasa.gov/.

Follow the link and fill in your personal details in the fields: First Name, Last Name, Email, and a key will be sent to you (as well as to your email address).
You need to use this key to make requests to the API.

So, to get a link to an image or other content, you need to:
1. Make a request at: https://api.nasa.gov/planetary/apod?api_key=ВАШ_КЛЮЧ
2. Analyze the response you receive
3. Find the `url` field in the response — it contains the address of the image or other content (e.g., video) that you need to download and save locally (on your computer). The name of the file to be saved should be taken from part of the URL (from the example below, DSC1028_PetersNEOWISEAuroralSpike_800.jpg)
4. Check that the saved file opens.

Example response from NASA
```json
{
  “copyright”: “Bill Peters”,
  “date”: “2020-07-17”,
  “explanation”: "After local midnight on July 14, comet NEOWISE was still above the horizon for Goldenrod, Alberta, Canada, just north of Calgary, planet Earth. In this snapshot it makes for an awesome night with dancing displays of the northern lights. The long-tailed comet and auroral displays are beautiful apparitions in the north these days. Both show the influence of spaceweather and the wind from the Sun. Skygazers have widely welcomed the visitor from the Oort cloud, though C/2020 F3 (NEOWISE) is in an orbit that is now taking it out of the inner Solar System.  Comet NEOWISE Images: July 16 | July 15 | July 14 | July 13 | July 12 | July 11 | July 10 & earlier",
  “hdurl”: “https://apod.nasa.gov/apod/image/2007/DSC1028_PetersNEOWISEAuroralSpike.jpg”,
  “media_type”: “image”,
  “service_version”: “v1”,
  “title”: “NEOWISE of the North”,
  “url”: “https://apod.nasa.gov/apod/image/2007/DSC1028_PetersNEOWISEAuroralSpike_800.jpg”
}
```

## What you need to do
1. Get a key for the NASA API at: https://api.nasa.gov/
2. Make a request from the code: https://api.nasa.gov/planetary/apod?api_key=ВАШ_КЛЮЧ
3. Create a response class and parse the JSON response using Jackson or Gson.
4. Find the url field in the response and download the byte array, which should be saved to a file.
5. The file name should be taken from the url.

## Implementation
1. Create a `maven` or `gradle` project and add the apache httpclient library to pom.xml or gradle.build

Example:
```text
<dependency>
   <groupId>org.apache.httpcomponents</groupId>
   <artifactId>httpclient</artifactId>
   <version>4.5.12</version>
</dependency>
```
2. Create a method in which you add and configure the `CloseableHttpClient` class, for example using a builder
```text
CloseableHttpClient httpClient = HttpClientBuilder.create()
    .setDefaultRequestConfig(RequestConfig.custom()
        .setConnectTimeout(5000)    // maximum wait time for connecting to the server
        .setSocketTimeout(30000)    // maximum wait time for receiving data
        .setRedirectsEnabled(false) // ability to follow redirects in the response
        .build())
    .build();
```
3. Add the request object HttpGet request = new HttpGet(“https://api.nasa.gov/planetary/apod?api_key=ВАШ_КЛЮЧ”) and
   call the remote service `CloseableHttpResponse response = httpClient.execute(request)`;
4. Add a library for working with json to pom.xml or gradle.build

Example:
```text
<dependency>
   <groupId>com.fasterxml.jackson.core</groupId>
   <artifactId>jackson-databind</artifactId>
   <version>2.11.1</version>
</dependency>
```
5. Create a class in which we will convert the JSON response from the server.
6. Convert JSON to a Java object.
7. Find the url field in the Java object and make another HTTP request with it using the already created httpClient.
8. Save the response body to a file named after the url part.
9. Check that the file downloads and opens.
