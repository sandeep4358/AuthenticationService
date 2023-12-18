//pipline with the run build command is added
pipeline{
    agent any
    stages{
        stage('SCM'){
            steps{
                echo ' getting code from the git repository'
                git changelog: false, poll: false, url: 'https://github.com/sandeep4358/AuthenticationService.git'
            }
        }


    stage('Maven Build'){
                steps{
                    sh 'mvn clean install'
                }
            }

     stage('Docker Build'){
                steps{
                    echo 'Docker build'
                    script{
                         withDockerRegistry(credentialsId: 'a77c722e-a2ea-45c3-b4e3-6100d91bcb67') {
                                                 // some block
                                                 sh 'docker image build -t sandeep022/authenticationService:${BUILD_NUMBER} .'
                                                 sh 'docker push sandeep022/authenticationService:${BUILD_NUMBER}'
                                             }
                    }
                }
		}

		stage('Docker Image Run '){
                steps{
                    echo 'Image Run'
                    script{
                                                 // some block
												 //sh 'docker container rm -f authenticationService'

                                                 sh 'docker run -dit --name authenticationService{BUILD_NUMBER} -p 8089:8080 sandeep022/authenticationService:${BUILD_NUMBER}'

                    }
                }
		}

}

post{
			always{
				emailext(
					subject:"Pipeline : ${BUILD_TAG} : Build Version ${BUILD_NUMBER}",
					body: '''<html>
							<body>
								<p>Build Status: ${BUILD_STATUS}</p>
								<p>Build Number: ${BUILD_NUMBER}</p>
								<p>Check the <a href="${BUILD_URL}">console output</a>.</p>

							</body>
							</html> ''',
					to: 'sandy.msit@gmail.com',
					from: 'sandy.msit@gmail.com',
					replyTo: 'freelanceratsany@gmail.com',
					mimeType: 'text/html'
				)


			}
		}


}