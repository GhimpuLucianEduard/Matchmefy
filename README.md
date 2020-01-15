# Matchmefy

Matchmefy -> get a "match" score with other spotify users

Uses Spotify Api - NO SERVER NEEDED FOR NOW

Use Cases:

login -> with spotify account

3 tabs:

Search & Match -> search other users and see the match score

Your Matches -> sorted list with past matches -> details for each match

Settings -> logout & invite friend

=================================

How the match score will work:

The data is collected each time the app is lunched (via spotify api endpoint)

The algorith will look into 2 things: artists and songs

We will lookup the top 50 artits and song for each user

Each time we find a artist in the other user top we add some points to the match score.
The amount of points is determineted by the rank of the artist in the other player top
e.g. if drake is top 5 in user A, it will get:
-10 points if its also top 5 in user B
-8 points if its 6-10
-7 pints for 10-25
etc..

same for songs

At the end we display the match score with some stats like: "you both like X, Y" (see how last.fm does it)
