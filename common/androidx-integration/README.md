# Directory that holds the output of the androidx SNAPSHOT build

This directory will be automatically populated with the output of building the androidx SNAPSHOT build when the androidx-studio-integration tests run on the androidx-studio-integration branch. This is done by the frameworks/busytown/androidx-studio-integration-tests.sh script in that branch.

The directory must remain empty in all branches as it will only be populated at build time.

This can be manually populated to run the tests in the Studio branch by downloading the androidx SNAPSHOT repository artifacts and unzipping them in the m2repository directory.

