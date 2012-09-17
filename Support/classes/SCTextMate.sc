SCTextMate {
	classvar nodes, <>matePath;

	*initClass {
		nodes = List[];

		Platform.case(\osx) {

			var whichMate = "which mate".unixCmdGetStdOut;
			if(whichMate.isEmpty){
				matePath = "/usr/local/bin/mate";
			} {
				matePath = whichMate;
			};

			matePath = matePath.replace("\n", "");
		}
	}

	// use this if you want to display the text without the html formatting
	*findRawHelp {|klass|
		var h, helpPath;
		var f, content;

		klass = klass.asString;

		h = Help.new;
		helpPath = h.findHelpFile(klass);

		if(helpPath.isNil){
			("No helpfile found for: "++klass).postln;
		} {
			f = File.open(helpPath, "r");

			// no line iteration in sc?
			content = f.readAllString;
			f.close;

			content = content.stripHTML;
			("echo \""++content++"\" | uniq | "++matePath++" -a").unixCmd(postOutput: false);
		}
	}


	*openClass{ |klass|
		// TM version only
		var fname, cmd;
		var allClasses = Class.allClasses.collect(_.asString);

		klass = klass.asString;

		if(allClasses.detect{ |str| str == klass }.notNil) { // .includes doesn't work?
			fname = klass.interpret.filenameSymbol;
			cmd = "grep -nh \"^" ++ klass ++ "\" \"" ++ fname ++ "\" > /tmp/grepout.tmp";
			cmd.unixCmd(postOutput: false, action: { 
				File.use("/tmp/grepout.tmp", "r") { |f|
					var content = f.readAllString;
					var split = content.split($:);
					if("^[0-9]+$".matchRegexp(split.first.asString)) {
						(matePath ++ " -l"++ split.first + "\"" ++ fname ++ "\"").unixCmd(postOutput: false);
					} {
						(matePath ++ " -l1 \"" ++ fname ++ "\"").unixCmd(postOutput: false);
					};
					f.close;
				};
			});
		}{"sorry class "++klass++" not found".postln}
	}

	// adapated from Kernel.sc
	*methodTemplates { |name, openInTextMate=true|
		var out, found = 0, namestring, fname;
		out = CollStream.new;
		out << "Implementations of '" << name << "' :\n";
		Class.allClasses.do({ arg class;
			class.methods.do({ arg method;
				if (method.name == name, {
					found = found + 1;
					namestring = class.name ++ ":" ++ name;
					out << "   " << namestring << " :     ";
					if (method.argNames.isNil or: { method.argNames.size == 1 }, {
						out << "this." << name;
						if (name.isSetter, { out << "(val)"; });
					},{
						out << method.argNames.at(0);
						if (name.asString.at(0).isAlpha, {
							out << "." << name << "(";
								method.argNames.do({ arg argName, i;
									if (i > 0, {
										if (i != 1, { out << ", " });
										out << argName;
									});
								});
								out << ")";
							},{
								out << " " << name << " ";
								out << method.argNames.at(1);
							});
						});
						out.nl;
					});
				});
			});
			if(found == 0)
			{
				Post << "\nNo implementations of '" << name << "'.\n";
			}
			{
				if(openInTextMate) {
					fname = "/tmp/" ++ Date.seed ++ ".sc";
					File.use(fname, "w") { |f|
						f << out.collection.asString;
						(matePath + fname).unixCmd(postOutput: false);
					};
				} {
					out.collection.newTextWindow(name.asString);
				};
			};
		}

		// adapted from Kernel.sc
		*methodReferences { |name, openInTextMate=true|
			var out, references, fname;
			name = name.asSymbol;
			out = CollStream.new;
			references = Class.findAllReferences(name);

			if (references.notNil, {
				out << "References to '" << name << "' :\n";
				references.do({ arg ref; out << "   " << ref.asString << "\n"; });

				if(openInTextMate) {
					fname = "/tmp/" ++ Date.seed ++ ".sc";
					File.use(fname, "w") { |f|
						f << out.collection.asString;
						(matePath + fname).unixCmd(postOutput: false);
					};
				} {
					out.collection.newTextWindow(name.asString);
				};
			},{
				Post << "\nNo references to '" << name << "'.\n";
			});
		}
	}
