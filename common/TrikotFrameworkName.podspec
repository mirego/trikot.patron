Pod::Spec.new do |spec|
    spec.name                     = 'TrikotFrameworkName'
    spec.version                  = '1.0.0'
    spec.homepage                 = 'www.mirego.com'
    spec.source                   = { :http=> ''}
    spec.authors                  = ''
    spec.license                  = 'BSD-3'
    spec.summary                  = 'Trikot.Patron'
    spec.vendored_frameworks      = 'build/cocoapods/framework/TrikotFrameworkName.framework'
    spec.libraries                = 'c++'
                
                
                
    spec.pod_target_xcconfig = {
        'KOTLIN_PROJECT_PATH' => ':common',
        'PRODUCT_MODULE_NAME' => 'TrikotFrameworkName',
    }
                
    spec.script_phases = [
        {
            :name => 'Build TrikotFrameworkName',
            :execution_position => :before_compile,
            :shell_path => '/bin/sh',
            :script => <<-SCRIPT
                if [ "YES" = "$COCOAPODS_SKIP_KOTLIN_BUILD" ]; then
                  echo "Skipping Gradle build task invocation due to COCOAPODS_SKIP_KOTLIN_BUILD environment variable set to \"YES\""
                  exit 0
                fi
                set -ev
                REPO_ROOT="$PODS_TARGET_SRCROOT"
                "$REPO_ROOT/../gradlew" -p "$REPO_ROOT" $KOTLIN_PROJECT_PATH:syncFramework \
                    -Pkotlin.native.cocoapods.platform=$PLATFORM_NAME \
                    -Pkotlin.native.cocoapods.archs="$ARCHS" \
                    -Pkotlin.native.cocoapods.configuration="$CONFIGURATION"
            SCRIPT
        }
    ]
    spec.resources = "src/commonMain/resources/translations/*"
end