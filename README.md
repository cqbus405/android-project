# android-project

Description

Using the Github Rest API (https://developer.github.com/v3/) to display a list of the the most starred repositories that were created between May 23 and May 29, 2016.
When one of the repository is selected in the list, display its top contributors with names, pictures and profile links.

api

1. Display a list of the the most starred repositories that were created the last week (05/23/16 - 05/29/16)
   https://api.github.com/search/repositories?q=created:"2016-05-23 .. 2016-05-29"&sort=starts&page=1&per_page=10

2. Display its top contributors with names, pictures and profile links
   https://api.github.com/repos/:owner(name)/:repo(name)/contributors&page=1&per_page=10

3rd party library

Retrofit
Picasso
Google Gson
