# SP Weather Application
 Take-Home-Test pack for Mobile Developer. This application has been developed at VISEO
 SP Weather application is use to see the current Weather for selected City.

# API
In order to get the weather information, we're using: 
https://www.worldweatheronline.com/developer/api/

## Developer Guidelines
We the official [Kotlin language documentation](https://kotlinlang.org/docs/kotlin-docs.pdf).

## Version Control
We follow GitFlow schema.

### Branches
- `master` for release versions.
- `develop` for current developments. 
- The branch structure will be like: 
`fix|feature|chore|tech|style|doc/feature/feature-id/branch-title`

- After the PR has been merged the owner is responsible for deleting his branch. It should be deleted within a reasonable time frame to keep the branch tree clean.

### Commit conventions
We follow the [angular commit message guidelines](https://github.com/angular/angular/blob/master/CONTRIBUTING.md#commit).
In case the commit is part of a User story, it should follow the pattern:
`<meta>(<feature>): #<feature-id> <subject>`

Some sample:
```
 fix(my-peers): #1226 Align error message for my peers
```
