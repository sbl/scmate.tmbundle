#!/usr/bin/ruby
# 
# SC:Pipe derived from sclang_pipe (SCVIM Package)
# Copyright 2007 Alex Norman under GPL
# 
# modified 2010 stephen lumenta to work with the supercollider-txtmate-bundle
# 
# http://github.com/sbl/supercollider-tmbundle

require 'fileutils'
require 'singleton'

module SC
  
  class Pipe 
    include Singleton
    
    @@pipe_loc = "/tmp/sclang-pipe"
    @@rundir = "/Applications/SuperCollider.app/Contents/Resources"
    @@pid_loc = "/tmp/sclangpipe_app-pid" 
        
    class << self
      def serve
        prepare_pipe
        run_pipe
        clean_up      
      end
      
      def pipe_loc
        @@pipe_loc
      end
      
      def pid_loc
        @@pid_loc
      end
            
      private
      
      def prepare_pipe
        File.open(@@pid_loc, "w"){ |f|
          f.puts Process.pid
        }

        #remove the pipe if it exists
        if File.exists?(@@pipe_loc)
          FileUtils.rm(@@pipe_loc)
        end
        #make a new pipe
        system("mkfifo", @@pipe_loc)
      end
   
      def run_pipe
        Dir.chdir(@@rundir)
        
        @@pipeproc = Proc.new {
          trap("INT") do
            Process.exit
          end
          
          IO.popen("./sclang -d #{@@rundir.chomp} -i scmate", "w") do |sclang|
            loop {
              x = `cat #{@@pipe_loc}`
              sclang.print x if x
            }
          end
        }
        $p = Process.fork { @@pipeproc.call }
      end

      def clean_up
        #if we get a hup then we kill the pipe process and
        #restart it
        trap("HUP") do
          Process.kill("INT", $p)
          $p = Process.fork { @@pipeproc.call }
        end

        #clean up after us
        trap("INT") do
          remove_files
          exit
        end
        #we sleep until a signal comes
        sleep(1) until false
      end
    
      def remove_files
        FileUtils.rm(@@pipe_loc)
        FileUtils.rm(@@pid_loc)
      end
    end
  end
end
