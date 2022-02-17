package tim.jarcomp;

import java.io.File;
import javax.swing.filechooser.FileFilter;


/**
 * Class to act as a generic file filter based on file extension
 */
public class GenericFileFilter extends FileFilter
{
	/** Filter description for display */
	private String _filterDesc = null;
	/** Array of allowed three-character suffixes */
	private String[] _threeCharSuffixes = null;
	/** Array of allowed four-character suffixes */
	private String[] _fourCharSuffixes = null;


	/**
	 * Constructor
	 * @param inDescription filter description
	 * @param inSuffixes array of allowed 3- and 4-character file suffixes
	 */
	public GenericFileFilter(String inDescription, String[] inSuffixes)
	{
		_filterDesc = inDescription;
		if (inSuffixes != null && inSuffixes.length > 0)
		{
			_threeCharSuffixes = new String[inSuffixes.length];
			_fourCharSuffixes = new String[inSuffixes.length];
			int threeIndex = 0, fourIndex = 0;
			for (int i=0; i<inSuffixes.length; i++)
			{
				String suffix = inSuffixes[i];
				if (suffix != null)
				{
					suffix = suffix.trim().toLowerCase();
					if (suffix.length() == 3) {
						_threeCharSuffixes[threeIndex++] = suffix;
					}
					else if (suffix.length() == 4) {
						_fourCharSuffixes[fourIndex++] = suffix;
					}
				}
			}
		}
	}

	/**
	 * Check whether to accept the specified file or not
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File inFile)
	{
		return inFile.isDirectory() || acceptFilename(inFile.getName());
	}

	/**
	 * Check whether to accept the given filename
	 * @param inName name of file
	 * @return true if accepted, false otherwise
	 */
	public boolean acceptFilename(String inName)
	{
		if (inName != null)
		{
			int nameLen = inName.length();
			if (nameLen > 4)
			{
				// Check for three character suffixes
				char currChar = inName.charAt(nameLen - 4);
				if (currChar == '.')
				{
					return acceptFilename(inName.substring(nameLen - 3).toLowerCase(), _threeCharSuffixes);
				}
				// check for four character suffixes
				currChar = inName.charAt(nameLen - 5);
				if (currChar == '.')
				{
					return acceptFilename(inName.substring(nameLen - 4).toLowerCase(), _fourCharSuffixes);
				}
			}
		}
		// Not matched so don't accept
		return false;
	}

	/**
	 * Check whether to accept the given filename
	 * @param inSuffixToCheck suffix to check
	 * @param inAllowedSuffixes array of allowed suffixes
	 * @return true if accepted, false otherwise
	 */
	public boolean acceptFilename(String inSuffixToCheck, String[] inAllowedSuffixes)
	{
		if (inSuffixToCheck != null && inAllowedSuffixes != null)
		{
			// Loop over allowed suffixes
			for (int i=0; i<inAllowedSuffixes.length; i++)
			{
				if (inAllowedSuffixes[i] != null && inSuffixToCheck.equals(inAllowedSuffixes[i]))
				{
					return true;
				}
			}
		}
		// Fallen through so not allowed
		return false;
	}


	/**
	 * Get description
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription()
	{
		return _filterDesc;
	}

}
